package dev.toke.ameplus.services

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import dev.toke.ameplus.R

class ExoPlayerService(context: Context) {
    private lateinit var exoPlayer: ExoPlayer

    init {
        exoPlayer = ExoPlayer.Builder(context).build()

    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun  playError() {
        setAudioFile(R.raw.lostitem)
    }

    fun playGoodSound() {
        setAudioFile(R.raw.correct_choice)
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun  setAudioFile(file: Int = R.raw.lostitem) {
        val errorFileUri = RawResourceDataSource.buildRawResourceUri(file).toString()
        exoPlayer.setMediaItem(MediaItem.fromUri(errorFileUri))
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }
}