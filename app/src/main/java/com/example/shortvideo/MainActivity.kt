package com.example.shortvideo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var videoList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        videoList = assets.list("video")?.toList() ?: emptyList()
        recyclerView.adapter = VideoAdapter(videoList, this)

        // scroll ke tengah untuk infinite loop aman
        recyclerView.scrollToPosition(Int.MAX_VALUE / 2)

        val btnUp: Button = findViewById(R.id.btnUp)
        val btnDown: Button = findViewById(R.id.btnDown)

        btnUp.setOnClickListener {
            val firstVisible = layoutManager.findFirstVisibleItemPosition()
            val holder = recyclerView.findViewHolderForAdapterPosition(firstVisible) as? VideoAdapter.VideoViewHolder
            holder?.videoView?.pause()
            recyclerView.smoothScrollToPosition(firstVisible - 1)
        }

        btnDown.setOnClickListener {
            val lastVisible = layoutManager.findLastVisibleItemPosition()
            val holder = recyclerView.findViewHolderForAdapterPosition(firstVisible) as? VideoAdapter.VideoViewHolder
            holder?.videoView?.pause()
            recyclerView.smoothScrollToPosition(lastVisible + 1)
        }
    }
}
