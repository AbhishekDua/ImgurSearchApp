package com.example.imgursearchapplication.ui;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imgursearchapplication.R;
import com.example.imgursearchapplication.db.PhotoData;
import com.example.imgursearchapplication.ui.CommentsAdapter;
import com.example.imgursearchapplication.viewmodels.ImageDetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageDetailActivity extends AppCompatActivity {
    TextView errorText;
    EditText commentBox;
    ImageView selectedImage;
    Button submitButton;
    RecyclerView commentsList;
    ImageDetailViewModel imageDetailViewModel;
    Toolbar toolbar;
    CommentsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PhotoData photoData = (PhotoData) getIntent().getExtras().get("photoData");
        errorText = findViewById(R.id.error_view);
        commentBox = findViewById(R.id.comment_box);
        selectedImage = findViewById(R.id.selected_image);
        submitButton = findViewById(R.id.submit_button);
        commentsList = findViewById(R.id.comments_view);
        if(photoData != null) {
            ImageDetailViewModel.ImageDetailFactory factory = new ImageDetailViewModel.ImageDetailFactory(this.getApplication(), photoData);
            imageDetailViewModel = new ViewModelProvider(this,factory).get(ImageDetailViewModel.class);
            showContent(photoData);
            subscribeViewModel();
        } else {
            showErrorView();
        }
    }

    private void subscribeViewModel() {
        imageDetailViewModel.getComments().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if(adapter == null) {
                    adapter = new CommentsAdapter(strings);
                    commentsList.setAdapter(adapter);
                } else {
                    adapter.updateComments(strings);
                }
            }
        });
    }

    private void showErrorView() {
        getSupportActionBar().setTitle("Error");
        errorText.setVisibility(View.VISIBLE);
        commentBox.setVisibility(View.GONE);
        selectedImage.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
        commentsList.setVisibility(View.GONE);
    }

    private void showContent(PhotoData data) {
        getSupportActionBar().setTitle(data.getTitle());
        errorText.setVisibility(View.GONE);
        commentBox.setVisibility(View.VISIBLE);
        selectedImage.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.VISIBLE);
        commentsList.setVisibility(View.VISIBLE);
        if(data.getImages() != null && data.getImages().size() > 0 && data.getImages().get(0).getLink() != null) {
            Picasso.get().load(data.getImages().get(0).getLink()).fit().placeholder(R.drawable.gray_background).into(selectedImage);
        }
        setupEditText();
        handleSubmitButton();
    }

    private void setupEditText() {
        commentBox.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_MULTI_LINE |
                InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        commentBox.setMaxLines(4);
        commentBox.setVerticalScrollBarEnabled(true);
        commentBox.setMovementMethod(new ScrollingMovementMethod());
    }

    private void handleSubmitButton() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String comment = commentBox.getText().toString().trim();
              imageDetailViewModel.addComment(comment);
              commentBox.setText("");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}