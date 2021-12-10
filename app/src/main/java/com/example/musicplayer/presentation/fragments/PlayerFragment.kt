package com.example.musicplayer.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentPlayerBinding
import com.example.musicplayer.domain.entities.Track
import com.example.musicplayer.presentation.PlayerViewModel
import com.example.musicplayer.utils.PlayerState
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.concurrent.TimeUnit

class PlayerFragment : Fragment() {
    private lateinit var binding: FragmentPlayerBinding
    private val viewModel by sharedViewModel<PlayerViewModel>()
    private var updateSeekBarJob: Job? = null
    private var updateCurrentTimeJob: Job? = null
    private var isSeekBarDirty = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playButton.setOnClickListener {
            viewModel.togglePause()
        }

        viewModel.currentTrackData.observe(viewLifecycleOwner, {
            binding.playerElements.visibility = View.INVISIBLE
            updateSongUI(it)
            viewModel.prepareSong()
        })

        viewModel.currentTrackDuration.observe(viewLifecycleOwner, {
            binding.seekBar.max = it
        })

        viewModel.playerState.observe(viewLifecycleOwner, {
            when (it) {
                PlayerState.PREPARED -> {
                    viewModel.setCurrentTrackDuration(viewModel.getSongDuration())
                    setupPlayerState(R.drawable.play_icon)
                }
                PlayerState.ENDED -> {
                    setupPlayerState(R.drawable.play_icon)
                }
                PlayerState.PLAYING -> {
                    setupPlayerState(R.drawable.pause_icon)
                }
                PlayerState.PAUSED -> {
                    setupPlayerState(R.drawable.play_icon)
                }
                else -> {

                }
            }
        })

        binding.menuButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupPlayerState(icon: Int) {
        binding.playerElements.visibility = View.VISIBLE
        binding.playButton.setImageResource(icon)
        if (isSeekBarDirty) {
            setSeekBar()
            isSeekBarDirty = false
        }
    }

    private fun updateSongUI(newTrack: Track) {
        binding.performerText.text = newTrack.performer
        binding.songName.text = newTrack.name

        val durationMinutes = TimeUnit.MILLISECONDS.toMinutes(newTrack.duration.toLong()) % 60
        val durationSeconds = TimeUnit.MILLISECONDS.toSeconds(newTrack.duration.toLong()) % 60

        binding.timeFull.text = String.format("%d:%02d", durationMinutes, durationSeconds)

        Picasso.get()
            .load(newTrack.coverURL)
            .into(binding.albumCover)
    }

    private fun setSeekBar() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    viewModel.seekInSong(p1)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        updateSeekBarJob = lifecycleScope.launch {
            while (isActive) {
                val currentPosition = viewModel.getPlayerPosition()
                binding.seekBar.progress = currentPosition
                delay(25)
            }
        }

        updateCurrentTimeJob = lifecycleScope.launch {
            while (isActive) {
                val currentPosition = viewModel.getPlayerPosition()
                val durationMinutes = TimeUnit.MILLISECONDS.toMinutes(currentPosition.toLong()) % 60
                val durationSeconds = TimeUnit.MILLISECONDS.toSeconds(currentPosition.toLong()) % 60

                binding.timeCurrent.text =
                    String.format("%d:%02d", durationMinutes, durationSeconds)

                delay(1000)
            }
        }
    }
}