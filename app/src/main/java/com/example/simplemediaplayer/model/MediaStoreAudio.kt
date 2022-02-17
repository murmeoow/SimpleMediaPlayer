package com.example.simplemediaplayer.model

import android.graphics.Bitmap
import android.net.Uri

data class MediaStoreAudio (
    val id: Long,
    val songTitle: String,
    val songArtist: String,
    val path: Uri ,
    val duration: Int,
    //val albumArt: Bitmap,
    val album: String
        )