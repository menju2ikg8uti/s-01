package com.example.shortvideo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var videoList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        // Scan video folder in assets
        videoList = assets.list("video")?.toList() ?: emptyList()

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = VideoAdapter(videoList, this)

        // Infinite scroll
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                if (lastVisible == videoList.size - 1) {
                    recyclerView.scrollToPosition(0)
                }
            }
        })
    }
}
