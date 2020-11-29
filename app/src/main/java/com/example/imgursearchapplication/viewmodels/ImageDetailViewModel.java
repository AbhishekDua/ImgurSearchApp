package com.example.imgursearchapplication.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.imgursearchapplication.db.DataSource;
import com.example.imgursearchapplication.db.LocalDataSource;
import com.example.imgursearchapplication.db.PhotoData;

import java.util.List;

public class ImageDetailViewModel extends AndroidViewModel {
    DataSource dataSource;
    private LiveData<List<String>> comments;
    private PhotoData photoData;

    public ImageDetailViewModel(@NonNull Application application, PhotoData data, DataSource localDataSource) {
        super(application);
        this.dataSource = localDataSource;
        photoData = data;
        comments = dataSource.getComments(data.getId());
    }

    public LiveData<List<String>> getComments() {
        return comments;
    }

    public void addComment(String comment) {
        if(!comment.isEmpty() && !photoData.getId().isEmpty()) {
            dataSource.addComment(photoData.getId(), comment);
        }
    }

    public static class ImageDetailFactory extends ViewModelProvider.AndroidViewModelFactory {

        Application app;
        PhotoData photoData;
        DataSource localDataSource;
        /**
         * Creates a {@code AndroidViewModelFactory}
         *
         * @param application an application to pass in {@link AndroidViewModel}
         */
        public ImageDetailFactory(@NonNull Application application, PhotoData data) {
            super(application);
            this.app = application;
            this.photoData = data;
            localDataSource = new LocalDataSource(application);
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ImageDetailViewModel(app, photoData, localDataSource);
        }
    }
}
