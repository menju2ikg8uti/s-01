package com.example.shortvideo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream

class VideoAdapter(private val videos: List<String>, private val context: Context) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoView: VideoView = view.findViewById(R.id.videoView)
        val tvName: TextView = view.findViewById(R.id.tvVideoName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoFile = videos[position % videos.size] // loop video
        holder.tvName.text = videoFile

        try {
            // Copy video dari assets ke cacheDir supaya VideoView bisa putar
            val file = File(context.cacheDir, videoFile)
            context.assets.open("video/$videoFile").use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }

            // Set video path ke file di cache
            holder.videoView.setVideoPath(file.absolutePath)
            holder.videoView.setOnPreparedListener {
                it.isLooping = true
                holder.videoView.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.videoView.setOnClickListener {
            if (holder.videoView.isPlaying) holder.videoView.pause() else holder.videoView.start()
        }
    }

    override fun getItemCount(): Int = Integer.MAX_VALUE // infinite scroll
}
