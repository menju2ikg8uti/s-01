package com.example.shortvideo

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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

        val uri = Uri.parse("file:///android_asset/video/$videoFile")
        holder.videoView.setVideoURI(uri)
        holder.videoView.setOnPreparedListener {
            it.isLooping = true
            holder.videoView.start()
        }

        holder.videoView.setOnClickListener {
            if (holder.videoView.isPlaying) holder.videoView.pause() else holder.videoView.start()
        }
    }

    override fun getItemCount(): Int = Integer.MAX_VALUE // infinite scroll
}
