package com.example.musicplayer.presentation

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
import com.squareup.picasso.Picasso

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
            findNavController().navigate(R.id.action_playerFragment_to_mainScreenFragment)
        }
    }

    private fun updateSongUI(newSong: Song?) {
        binding.performerText.text = newSong?.performer
        binding.songName.text = newSong?.name

        Picasso.get()
            .load(newSong?.coverURL)
            .into(binding.albumCover)
    }
}