package com.example.simplemediaplayer.ui

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplemediaplayer.R
import com.example.simplemediaplayer.databinding.FragmentMusicListBinding
import com.example.simplemediaplayer.ui.adapter.AudioAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MusicListFragment : Fragment() {

    private lateinit var  binding: FragmentMusicListBinding
    private val viewModel: AudioPlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestPermissions()
        binding = FragmentMusicListBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        Log.d("TAG", viewModel.toString())

        val adapter = AudioAdapter(findNavController())

        viewModel.songs.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        binding.recyclerView.adapter = adapter

        return binding.root
    }


    private fun requestPermissions(){
        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted ->
            if (isGranted){
                viewModel.setSongList()
            }else{
                Toast.makeText(requireContext(), "Apply the permissions", Toast.LENGTH_SHORT).show()
            }
        }
        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

}