package com.example.shortvideo

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView

class VideoAdapter(private val context: Context, private val videoList: List<VideoModel>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoView: VideoView = itemView.findViewById(R.id.videoView)
        val container: FrameLayout = itemView.findViewById(R.id.videoContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videoList[position]

        val uri = Uri.parse("android.resource://${context.packageName}/raw/${video.fileName}")
        holder.videoView.setVideoURI(uri)

        val mediaController = MediaController(context)
        mediaController.setAnchorView(holder.videoView)
        holder.videoView.setMediaController(null) // Hide controller
        holder.videoView.setOnPreparedListener {
            it.isLooping = true
            holder.videoView.start()
        }

        holder.videoView.setOnClickListener {
            if (holder.videoView.isPlaying) holder.videoView.pause() else holder.videoView.start()
        }
    }

    override fun getItemCount(): Int = videoList.size
}
