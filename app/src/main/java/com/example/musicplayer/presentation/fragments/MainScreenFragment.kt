package com.example.musicplayer.presentation.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
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
import com.example.musicplayer.presentation.LoginViewModel
import com.example.musicplayer.presentation.PlayerViewModel
import com.example.musicplayer.utils.DialogueWindowManager
import com.example.musicplayer.utils.SongListAdapter
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurePopupMenu()

        val recyclerView = binding.songListRV
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = SongListAdapter(this)

        playerViewModel.getAllTracks().observe(viewLifecycleOwner, {
            (recyclerView.adapter as SongListAdapter).updateSongList(it)
        })

        binding.exitButton.setOnClickListener {
            loginViewModel.setLoginState(false)
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun configurePopupMenu() {
        val popupMenu = PopupMenu(requireContext(), binding.menuButton)
        popupMenu.inflate(R.menu.player_popup)
        popupMenu.gravity = Gravity.END

        binding.menuButton.setOnClickListener {
            popupMenu.show()
        }

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.getSongFromAPI -> {
                    showEnterTrackDataDialogue(requireContext())
                }
                R.id.provideToken -> {
                    showEnterSpotifyToken(requireContext())
                }
            }
            false
        }
    }

    private fun showEnterSpotifyToken(context: Context) {
        val builder = AlertDialog.Builder(context)

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        val input = EditText(context)
        input.hint = "Введите ваш Spotify Token"
        input.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(input)

        builder.setView(layout)

        builder.setTitle("Ввод данных")
        builder.setPositiveButton("Ок") { _, _ ->
            playerViewModel.provideSpotifyToken(input.text.toString())
        }
        builder.create()
        builder.show()
    }

    private fun showEnterTrackDataDialogue(context: Context) {
        val builder = AlertDialog.Builder(context)

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        val input = EditText(context)
        input.hint = "Введите название трека"
        input.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(input)

        val input2 = EditText(context)
        input2.hint = "Введите исполнителя трека"
        input2.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(input2)

        builder.setView(layout)

        builder.setTitle("Ввод данных")
        builder.setPositiveButton("Ок") { _, _ ->
            playerViewModel.addSongFromSpotifyAPI(
                "track:\"${input.text}\"+artist:\"${input2.text}\""
            )
        }
        builder.create()
        builder.show()
    }
}