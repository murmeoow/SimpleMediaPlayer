package com.example.simplemediaplayer.ui

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import androidx.lifecycle.*
import com.example.simplemediaplayer.model.MediaStoreAudio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AudioPlayerViewModel @Inject constructor(application: Application): AndroidViewModel(application) {

    private val _songs = MutableLiveData<List<MediaStoreAudio>>()
    val songs: LiveData<List<MediaStoreAudio>> get() = _songs

    fun loadSongs(){
        viewModelScope.launch {
            val songsList = querySongs()
            _songs.value = songsList
        }
    }

    private suspend fun querySongs(): List<MediaStoreAudio>{
        val songs = mutableListOf<MediaStoreAudio>()

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
                    val duration = cursor.getInt(durationColumn)


                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
                    )

                    val audio = MediaStoreAudio(id,title,artist,contentUri,duration,album)

                    songs+=audio
                }
            }

        }
        return songs
    }
}