package com.example.simplemediaplayer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplemediaplayer.databinding.FragmentMusicListBinding
import com.example.simplemediaplayer.ui.adapter.SongsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicListFragment : Fragment() {

    private lateinit var  binding: FragmentMusicListBinding
    private val adapter : AudioAdapter by lazy { AudioAdapter() }
    private val viewModel: AudioPlayerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicListBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)


        viewModel.loadSongs()
        viewModel.songs.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        binding.recyclerView.adapter = adapter

        return binding.root
    }



}