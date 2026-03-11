package com.example.shortvideo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class VideoAdapter(private val videos: List<String>, private val context: Context) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoView: VideoView = itemView.findViewById(R.id.videoView)
        val txtFileName: TextView = itemView.findViewById(R.id.txtFileName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val fileName = videos[position]

        // tampilkan nama file
        holder.txtFileName.text = fileName

        // copy video dari assets ke cache agar bisa play
        val file = File(context.cacheDir, fileName)
        if (!file.exists()) {
            context.assets.open("video/$fileName").use { input ->
                file.outputStream().use { output -> input.copyTo(output) }
            }
        }

        holder.videoView.setVideoPath(file.absolutePath)
        holder.videoView.start()

        // klik untuk pause/play
        holder.videoView.setOnClickListener {
            if (holder.videoView.isPlaying) holder.videoView.pause()
            else holder.videoView.start()
        }

        // loop video
        holder.videoView.setOnCompletionListener { holder.videoView.start() }
    }

    override fun getItemCount(): Int = videos.size
}
