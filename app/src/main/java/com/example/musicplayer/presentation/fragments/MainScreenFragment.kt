package com.example.musicplayer.presentation.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentMainScreenBinding
import com.example.musicplayer.helpers.DialogueWindowManager
import com.example.musicplayer.helpers.SongListAdapter
import com.example.musicplayer.presentation.LoginViewModel
import com.example.musicplayer.presentation.PlayerViewModel
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
        configurePopupMenu()
        configureNavigationMenu()

        val recyclerView = binding.songListRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = SongListAdapter()

        playerViewModel.playerDatabase.playerDBDao().getSongsList().observe(viewLifecycleOwner, {
            (recyclerView.adapter as SongListAdapter).updateSongList(it)
        })
    }

    private fun configureNavigationMenu() {
        binding.bottomMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_2 -> {
                    playerViewModel.setCurrentMenuItem(R.id.page_2)
                    findNavController().navigate(R.id.action_mainScreenFragment_to_searchFragment)
                }
                R.id.page_3 -> {
                    playerViewModel.setCurrentMenuItem(R.id.page_3)
                    findNavController().navigate(R.id.action_mainScreenFragment_to_favouritesFragment)
                }
            }
            false
        }

        playerViewModel.currentMenuItem.observe(viewLifecycleOwner, {
            binding.bottomMenu.menu.findItem(it).isChecked = true
        })
    }

    private fun configurePopupMenu() {
        val popupMenu = PopupMenu(requireContext(), binding.menuButton)
        popupMenu.inflate(R.menu.player_popup)

        binding.menuButton.setOnClickListener {
            popupMenu.show()
        }

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.exit -> {
                    loginViewModel.loginStateRepository.saveLoginState(false)
                    findNavController().navigate(R.id.action_mainScreenFragment_to_loginFragment)
                }
                R.id.getSongFromAPI -> {
                    showEnterIDDialogue(requireContext(), 0)
                }
                R.id.getAlbumFromAPI -> {
                    showEnterIDDialogue(requireContext(), 1)
                }
            }
            false
        }
    }

    private fun showEnterIDDialogue(context: Context, mode: Int) {
        val builder = AlertDialog.Builder(context)

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        val input = EditText(context)
        input.hint = "Введите ID трека"
        input.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(input)

        val input2 = EditText(context)
        input2.hint = "Введите ваш Spotify Token"
        input2.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(input2)

        builder.setView(layout)

        builder.setTitle("Добавить трек по ID")
        builder.setPositiveButton("Ок") { _, _ ->
            if (mode == 0) {
                playerViewModel.addSong(input.text.toString(), input2.text.toString())
            } else if (mode == 1) {
                playerViewModel.addAlbum(input.text.toString(), input2.text.toString())
            }
        }
        builder.create()
        builder.show()
    }
}