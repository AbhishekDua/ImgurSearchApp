package com.example.imgursearchapplication.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imgursearchapplication.R
import com.example.imgursearchapplication.db.PhotoData
import com.example.imgursearchapplication.ui.ImageDetailActivity
import com.example.imgursearchapplication.viewmodels.PhotosViewModel
import com.example.mysearchapplication.ImageAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),
    ImageClickCallback {

    private lateinit var imagesGrid: RecyclerView
    private lateinit var adapter: ImageAdapter
    private lateinit var searchBox: AppCompatEditText
    private lateinit var searchButton: ImageButton
    private val photosViewModel: PhotosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val utility = NetworkUtility(applicationContext)
        utility.registerNetworkCallback()
        searchBox = image_search_box
        searchButton = search_btn
        imagesGrid = image_grid
        imagesGrid.layoutManager = GridLayoutManager(this, 3)
        adapter = ImageAdapter(this)
        imagesGrid.adapter = adapter
        searchBox.addTextChangedListener(DebounceTextWatcher(this.lifecycle) {
                text ->
            text?.let {
                if (it.isNotEmpty() && it.length >= 3) {
                    photosViewModel.setQuery(it)
                }
            }
        })
        searchButton.setOnClickListener {
            val query = searchBox.text?.trim() ?: ""
            photosViewModel.setQuery(query.toString())
        }
        photosViewModel.photoList.observe(this, Observer {
            adapter.submitList(it)
        })
        photosViewModel.photoNetworkStatus.observe(this, Observer {
            if(it.isNotEmpty() && it.contains("false")) {
                Toast.makeText(this, "Unable to fetch Data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menu?.add(0, 3, Menu.NONE, " 3 ")
        menu?.add(0, 4, Menu.NONE, " 4 ")
        menu?.add(0, 6, Menu.NONE, " 6 ")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            3 -> {
                imagesGrid.layoutManager = GridLayoutManager(this, 3)
            }
            4 -> {
                imagesGrid.layoutManager = GridLayoutManager(this, 4)
            }
            6 -> {
                imagesGrid.layoutManager = GridLayoutManager(this, 6)

            }
            else -> {
                imagesGrid.layoutManager = GridLayoutManager(this, 3)

            }
        }
        return false
    }

    inner class DebounceTextWatcher(
        lifecycle: Lifecycle,
        private val onDebouncingQueryTextChange: (String?) -> Unit
    ) : TextWatcher {
        var debouncePeriod: Long = 250
        private var searchJob: Job? = null
        private val coroutineScope = lifecycle.coroutineScope
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(count >= 3 ) {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    s?.let {
                        delay(debouncePeriod)
                        onDebouncingQueryTextChange(it.toString())
                    }
                }
            }
        }
    }

    override fun onClick(photoData: PhotoData) {
        if(!photoData.images.isNullOrEmpty() && photoData.id.isNotEmpty()) {
            val intent = Intent(this, ImageDetailActivity::class.java)
            intent.putExtra("photoData", photoData)
            startActivity(intent)
        }
    }
}