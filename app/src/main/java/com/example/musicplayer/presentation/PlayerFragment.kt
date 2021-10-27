package com.example.musicplayer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.*
import com.example.musicplayer.data.JsonDataRepository
import com.example.musicplayer.databinding.FragmentPlayerBinding
import com.example.musicplayer.helpers.DialogueWindowManager

class PlayerFragment : Fragment() {
    private lateinit var binding: FragmentPlayerBinding
    private val viewModel: PlayerViewModel by viewModels()
    private lateinit var jsonDataRepository: JsonDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jsonDataRepository = JsonDataRepository(requireContext())
        jsonDataRepository.loadData()

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    DialogueWindowManager.showExitDialogue(requireContext())
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
        viewModel.changeSongData(jsonDataRepository.songs[0])

        binding.exitButton.setOnClickListener {
            (activity as MainActivity).loginStateRepository.saveLoginState(false)
            this.findNavController().navigate(R.id.action_playerFragment_to_loginFragment)
        }

        viewModel.songData.observe(viewLifecycleOwner, {
            binding.performerText.text = it.performer
            binding.songName.text = it.name
            val id = resources.getIdentifier(
                "@drawable/${it.coverPath}",
                null,
                activity?.packageName
            )
            binding.albumCover.setImageResource(id)
        })

        binding.nextButton.setOnClickListener {
            if (viewModel.currentSong.value!! < jsonDataRepository.songs.size - 1) {
                viewModel.currentSong.postValue(viewModel.currentSong.value!! + 1)
            } else {
                viewModel.currentSong.postValue(0)
            }
            viewModel.changeSongData(jsonDataRepository.songs[viewModel.currentSong.value!!])
        }
    }
}