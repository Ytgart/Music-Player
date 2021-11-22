package com.example.musicplayer.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentLoginBinding
import com.example.musicplayer.helpers.DialogueWindowManager
import dev.chrisbanes.insetter.applyInsetter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment() : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel by sharedViewModel<LoginViewModel>()

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

        var isShowToast = false

        binding.loginButton.setOnClickListener {
            loginViewModel.tryLoginUser(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
            if (!isShowToast) isShowToast = true
        }

        loginViewModel.isLoginSuccessful.observe(viewLifecycleOwner, {
            if (it) {
                findNavController().navigate(R.id.action_loginFragment_to_mainScreenFragment)
            } else {
                if (isShowToast) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.incorrect_login_or_password_alert),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        binding.newAccountButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}