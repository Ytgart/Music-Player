package com.example.musicplayer.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.*
import com.example.musicplayer.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginManager = LoginManager(requireContext())

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.inputFieldList
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = EditTextListAdapter(
            arrayOf(
                getString(R.string.login_text_field),
                getString(R.string.password_text_field)
            ), arrayOf(1, 1 or 128), arrayOf(false, true)
        )

        val rvAdapter = recyclerView.adapter as EditTextListAdapter

        binding.loginButton.setOnClickListener {

            val userDataFromDB = (activity as MainActivity).getPlayerUserDao()
                .getUserByLogin(rvAdapter.getTypedTexts()[0])

            if (loginManager.checkLoginData(
                    rvAdapter.getTypedTexts()[0],
                    rvAdapter.getTypedTexts()[1],
                    userDataFromDB
                )
            ) {
                loginManager.saveLoginState(true)
                this.findNavController()
                    .navigate(R.id.action_loginFragment_to_playerFragment)
            } else DialogueWindowManager.showAlert(
                getString(R.string.incorrect_login_or_password_alert),
                requireContext()
            )
        }

        binding.newAccountButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}