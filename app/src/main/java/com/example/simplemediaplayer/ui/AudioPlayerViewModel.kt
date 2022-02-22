package com.example.simplemediaplayer.ui

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.*
import com.example.simplemediaplayer.model.Audio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class AudioPlayerViewModel @Inject constructor(application: Application): AndroidViewModel(application) {

    private val _songs = MutableLiveData<List<Audio>>()
    val songs: LiveData<List<Audio>> get() = _songs

     fun setSongList() = viewModelScope.launch {
        _songs.value = querySongs()
    }

    private suspend fun querySongs(): List<Audio>{
        val songs = mutableListOf<Audio>()

        withContext(Dispatchers.IO){
            val projection = arrayOf(
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION
            )
            val selection = null
            val selectionArgs = null
            val sortOrder = null

            getApplication<Application>().contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, selection, selectionArgs, sortOrder
            )?.use {
                cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

                while (cursor.moveToNext()){
                    val id = cursor.getLong(idColumn)
                    val title = cursor.getString(titleColumn)
                    val artist = cursor.getString(artistColumn)
                    val album = cursor.getString(albumColumn)
                    val duration = cursor.getLong(durationColumn)


                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
                    )

                    val audio = Audio(id,title,artist,contentUri,duration,album)

                    songs+=audio
                }
            }

        }
        return songs
    }

//    fun setSongList()= viewModelScope.launch {
//        _songs.value = querySongs()
//    }
}