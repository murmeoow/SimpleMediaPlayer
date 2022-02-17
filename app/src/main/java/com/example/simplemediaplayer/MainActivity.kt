package com.example.simplemediaplayer

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.simplemediaplayer.ui.MusicListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()
    }

    private fun setUpFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, MusicListFragment())
            .commit()
    }

    private fun requestPermissions(){
        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
                isGranted ->
                if (isGranted) setUpFragment()
        }
        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}