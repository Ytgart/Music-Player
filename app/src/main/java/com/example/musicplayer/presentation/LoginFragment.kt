package com.example.musicplayer.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.*
import com.example.musicplayer.databinding.FragmentLoginBinding
import com.example.musicplayer.helpers.DialogueWindowManager
import com.example.musicplayer.helpers.UserDataValidator
import dev.chrisbanes.insetter.applyInsetter

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var validator: UserDataValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        validator = UserDataValidator()

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
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.root.applyInsetter {
                type(navigationBars = true) {
                    margin()
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val userDataFromDB =
                (activity as MainActivity)
                    .playerUserDao.getUserByLogin(binding.loginEditText.text.toString())
            if (validator.checkLoginData(
                    binding.loginEditText.text.toString(),
                    binding.passwordEditText.text.toString(),
                    userDataFromDB
                )
            ) {
                this.findNavController().navigate(R.id.action_loginFragment_to_mainScreenFragment)
                (activity as MainActivity).loginStateRepository.saveLoginState(true)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.incorrect_login_or_password_alert),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.newAccountButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}