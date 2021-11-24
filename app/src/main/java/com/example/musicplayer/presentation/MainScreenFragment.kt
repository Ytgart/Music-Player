package com.example.musicplayer.presentation

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
import com.example.musicplayer.databinding.FragmentMainScreenBinding
import com.example.musicplayer.helpers.DialogueWindowManager
import com.example.musicplayer.helpers.SongListAdapter
import dev.chrisbanes.insetter.applyInsetter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainScreenFragment : Fragment() {
    private lateinit var binding: FragmentMainScreenBinding
    private val loginViewModel by sharedViewModel<LoginViewModel>()
    private val playerViewModel by sharedViewModel<PlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)

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

        val popupMenu = PopupMenu(requireContext(), binding.menuButton)
        popupMenu.inflate(R.menu.player_popup)

        binding.menuButton.setOnClickListener {
            popupMenu.show()
        }

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.exit -> {
                    loginViewModel.setLoginState(false)
                    findNavController().navigate(R.id.action_mainScreenFragment_to_loginFragment)
                }
            }
            false
        }

        val recyclerView = binding.songListRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        playerViewModel.songList.observe(viewLifecycleOwner, {
            recyclerView.adapter = SongListAdapter(it)
        })
    }
}