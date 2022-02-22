package com.example.simplemediaplayer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemediaplayer.R
import com.example.simplemediaplayer.Utils
import com.example.simplemediaplayer.databinding.SongItemBinding
import com.example.simplemediaplayer.model.Audio
import com.example.simplemediaplayer.ui.MusicListFragmentDirections

class AudioAdapter(val navController: NavController): ListAdapter<Audio, AudioAdapter.AudioViewHolder>(SongDiffCallback()) {

    inner class AudioViewHolder(item: View): RecyclerView.ViewHolder(item){

        val binding = SongItemBinding.bind(item)

        fun bind(audio: Audio) = with(binding){
            binding.tvSongTitle.text = audio.songTitle
            binding.tvSongArtist.text = audio.songArtist

            val duration = audio.duration
            binding.tvSongDuration.text = Utils.durationConverter(duration)

            binding.fragmentSongItem.setOnClickListener {

                navController.navigate(
                    MusicListFragmentDirections.actionMusicListFragmentToPlayerFragment(currentList.indexOf(audio)))
            }
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

class SongDiffCallback : DiffUtil.ItemCallback<Audio>() {
    override fun areItemsTheSame(oldItem: Audio, newItem: Audio): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Audio, newItem: Audio): Boolean {
        return oldItem == newItem
    }
}