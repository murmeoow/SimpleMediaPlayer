package com.example.simplemediaplayer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemediaplayer.R
import com.example.simplemediaplayer.databinding.SongItemBinding
import com.example.simplemediaplayer.model.MediaStoreAudio

class AudioAdapter: ListAdapter<MediaStoreAudio, AudioAdapter.AudioViewHolder>(SongDiffCallback()) {

    inner class AudioViewHolder(item: View): RecyclerView.ViewHolder(item){

        val binding = SongItemBinding.bind(item)

        fun bind(audio: MediaStoreAudio) = with(binding){
            binding.tvSongTitle.text = audio.songTitle
            binding.tvSongArtist.text = audio.songArtist
            binding.tvSongDuration.text = audio.duration.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return AudioViewHolder(view)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SongDiffCallback : DiffUtil.ItemCallback<MediaStoreAudio>() {
    override fun areItemsTheSame(oldItem: MediaStoreAudio, newItem: MediaStoreAudio): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MediaStoreAudio, newItem: MediaStoreAudio): Boolean {
        return oldItem == newItem
    }
}