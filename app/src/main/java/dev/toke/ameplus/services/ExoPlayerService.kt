package dev.toke.ameplus.services

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import dev.toke.ameplus.R

class ExoPlayerService(exoPlayer: ExoPlayer) {
    private lateinit var _exoPlayer: ExoPlayer

    init {
        _exoPlayer = exoPlayer

    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun  playError() {
        setAudioFile(R.raw.doh)
    }

    fun playGoodSound() {
        setAudioFile(R.raw.woohoo)
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun  setAudioFile(file: Int = R.raw.woohoo) {
        val errorFileUri = RawResourceDataSource.buildRawResourceUri(file).toString()
        _exoPlayer.setMediaItem(MediaItem.fromUri(errorFileUri))
        _exoPlayer.prepare()
        _exoPlayer.playWhenReady = true
    }
}