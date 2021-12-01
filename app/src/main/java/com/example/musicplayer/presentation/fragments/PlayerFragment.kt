package com.example.musicplayer.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.R
import com.example.musicplayer.data.database.Song
import com.example.musicplayer.databinding.FragmentPlayerBinding
import com.example.musicplayer.domain.PlayerState
import com.example.musicplayer.presentation.PlayerViewModel
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
    private var isSeekBarDirty = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    resetPlayer()
                }
            })
    }

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

        viewModel.currentSongData.observe(viewLifecycleOwner, {
            updateSongUI(it)
        })

        viewModel.playerState.observe(viewLifecycleOwner, {
            when (it) {
                PlayerState.NOT_PREPARED -> viewModel.prepareSong()
                PlayerState.PREPARED -> {
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
                else -> {}
            }
        })

        binding.menuButton.setOnClickListener {
            resetPlayer()
        }
    }

    private fun resetPlayer() {
        updateSeekBarJob?.cancel()
        viewModel.playerState.removeObservers(viewLifecycleOwner)
        viewModel.resetPlayer()
        findNavController().navigate(R.id.mainScreenFragment)
    }

    private fun setupPlayerState(icon: Int) {
        binding.playButton.setImageResource(icon)
        binding.playerElements.visibility = View.VISIBLE
        if (isSeekBarDirty) {
            setSeekBar()
            isSeekBarDirty = false
        }
    }

    private fun updateSongUI(newSong: Song) {
        binding.performerText.text = newSong.performer
        binding.songName.text = newSong.name

        val durationMinutes = TimeUnit.MILLISECONDS.toMinutes(newSong.duration.toLong()) % 60
        val durationSeconds = TimeUnit.MILLISECONDS.toSeconds(newSong.duration.toLong()) % 60

        binding.timeFull.text = String.format("%d:%02d", durationMinutes, durationSeconds)

        Picasso.get()
            .load(newSong.coverURL)
            .into(binding.albumCover)
    }

    private fun setSeekBar() {
        binding.seekBar.max = viewModel.getSongDuration()
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
    }
}