package com.example.shortvideo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VideoAdapter
    private val videoList = mutableListOf<VideoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.videoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        loadVideos()
        adapter = VideoAdapter(this, videoList)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) { // scroll end
                    Collections.shuffle(videoList)
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun loadVideos() {
        val assetManager = assets
        val files = assetManager.list("video")
        files?.forEach { file ->
            val nameWithoutExt = file.substringBeforeLast(".")
            videoList.add(VideoModel(nameWithoutExt))
        }
        Collections.shuffle(videoList)
    }
}
