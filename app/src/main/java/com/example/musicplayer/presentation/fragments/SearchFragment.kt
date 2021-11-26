package com.example.musicplayer.presentation.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentSearchBinding
import com.example.musicplayer.helpers.DialogueWindowManager
import com.example.musicplayer.helpers.SongListAdapter
import com.example.musicplayer.helpers.afterTextChanged
import com.example.musicplayer.presentation.LoginViewModel
import com.example.musicplayer.presentation.PlayerViewModel
import dev.chrisbanes.insetter.applyInsetter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val playerViewModel by sharedViewModel<PlayerViewModel>()
    private val rvAdapter = SongListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    binding.searchEditTextLayout.clearFocus()
                    binding.statusTextView.visibility = View.VISIBLE
                    binding.statusTextView.text = "Начните вводить запрос.."
                    binding.searchEditText.setText("")
                    rvAdapter.updateSongList(listOf())
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        binding.topText.applyInsetter {
            type(statusBars = true) {
                margin()
            }
        }

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.root.applyInsetter {
                type(navigationBars = true) {
                    margin()
                }
            }
            return binding.root
        }

        binding.bottomMenu.applyInsetter {
            type(navigationBars = true) {
                margin()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureNavigationMenu()

        binding.searchEditText.afterTextChanged {
            if (it.isNotEmpty()) {
                searchForSongs(it)
            }
        }

        val recyclerView = binding.songListRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = rvAdapter
    }

    private fun searchForSongs(query: String) {
        playerViewModel.searchForSongs(query).observe(viewLifecycleOwner, {
            if (it.isEmpty()) {
                binding.statusTextView.visibility = View.VISIBLE
                binding.statusTextView.text = "Ничего не найдено"
            } else {
                binding.statusTextView.visibility = View.INVISIBLE
            }
            rvAdapter.updateSongList(it)
        })
    }

    private fun configureNavigationMenu() {
        binding.bottomMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    playerViewModel.setCurrentMenuItem(R.id.page_1)
                    findNavController().navigate(R.id.action_searchFragment_to_mainScreenFragment)
                }
                R.id.page_3 -> {
                    playerViewModel.setCurrentMenuItem(R.id.page_3)
                    findNavController().navigate(R.id.action_searchFragment_to_favouritesFragment)
                }
            }
            false
        }

        playerViewModel.currentMenuItem.observe(viewLifecycleOwner, {
            binding.bottomMenu.menu.findItem(it).isChecked = true
        })
    }
}