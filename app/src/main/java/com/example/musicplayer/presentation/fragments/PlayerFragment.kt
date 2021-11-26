package com.example.musicplayer.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.R
import com.example.musicplayer.data.database.Song
import com.example.musicplayer.databinding.FragmentPlayerBinding
import com.example.musicplayer.di.playerVMModule
import com.example.musicplayer.presentation.PlayerViewModel
import com.squareup.picasso.Picasso
import java.util.concurrent.TimeUnit

class PlayerFragment : Fragment() {
    private lateinit var binding: FragmentPlayerBinding
    private val viewModel: PlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentSongData.observe(viewLifecycleOwner, {
            updateSongUI(it)
        })

        binding.menuButton.setOnClickListener {
            findNavController().navigate(R.id.mainScreenFragment)
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
}