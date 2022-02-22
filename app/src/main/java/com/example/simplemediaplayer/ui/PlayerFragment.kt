package com.example.simplemediaplayer.ui

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.simplemediaplayer.R
import com.example.simplemediaplayer.databinding.FragmentPlayerBinding
import com.example.simplemediaplayer.model.Audio
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    private lateinit var binding: FragmentPlayerBinding
    private var songIndex: Int = -1
    private lateinit var songList: List<Audio>
    private val viewModel: AudioPlayerViewModel by activityViewModels()
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)

        val args = requireArguments()
        songIndex = args.getInt("songIndex")

        viewModel.songs.observe(requireActivity(), {
            songList = it
        })

        setUpUI(songIndex)

        setUpMediaPlayer()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibPause.setOnClickListener {
            pauseMusic()
        }

        binding.ibNext.setOnClickListener {
            playNextMusic()
            playMusic(songIndex)
        }

        binding.ibPrevious.setOnClickListener {
            playPreviousMusic()
            playMusic(songIndex)
        }

    }

    private fun setUpUI(index: Int){
        binding.tvTitle.text = songList[index].songTitle
        binding.tvArtist.text = songList[index].songArtist
    }

    private fun setUpMediaPlayer(){
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(requireContext(), songList[songIndex!!].path)
        }

        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }
    }

    private fun pauseMusic(){
        if (mediaPlayer.isPlaying){
            mediaPlayer.pause()
        }
        else{
            mediaPlayer.start()
        }
    }

    private fun playPreviousMusic(){
        if (songIndex != 0) {
            songIndex--
        } else{
            songIndex = songList.lastIndex
        }
    }

    private fun playNextMusic(){
        if (songList.lastIndex != songIndex) {
            songIndex++
        } else{
            songIndex = 0
        }
    }

    private fun playMusic(index : Int){
        setUpUI(index)
        mediaPlayer.reset()
        mediaPlayer.setDataSource(requireContext(), songList[index].path)
        mediaPlayer.prepareAsync()
    }

}