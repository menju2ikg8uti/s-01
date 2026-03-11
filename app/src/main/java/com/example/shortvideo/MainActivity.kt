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

        // Tombol scroll atas
        val btnUp: Button = findViewById(R.id.btnUp)
        btnUp.setOnClickListener {
            val firstVisible = layoutManager.findFirstVisibleItemPosition()
            val prev = if (firstVisible > 0) firstVisible - 1 else videoList.size - 1
            recyclerView.smoothScrollToPosition(prev)
        }

        // Tombol scroll bawah
        val btnDown: Button = findViewById(R.id.btnDown)
        btnDown.setOnClickListener {
            val lastVisible = layoutManager.findLastVisibleItemPosition()
            val next = if (lastVisible < videoList.size - 1) lastVisible + 1 else 0
            recyclerView.smoothScrollToPosition(next)
        }
    }
}
