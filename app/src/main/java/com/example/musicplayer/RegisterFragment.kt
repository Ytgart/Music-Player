package com.example.musicplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.databinding.FragmentRegisterBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RegisterFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var loginManager: LoginManager
    private val userDataValidator = UserDataValidator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginManager = LoginManager(requireActivity())

        if (loginManager.isLogged()) {
            this.findNavController()
                .navigate(R.id.action_registerFragment_to_playerFragment)
        }

        val recyclerView = binding.inputFieldList
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = EditTextListAdapter(
            arrayOf(
                getString(R.string.login_text_field),
                getString(R.string.email_text_field),
                getString(R.string.password_text_field),
                getString(R.string.repeat_password_text_field)
            ), arrayOf(1, 1 or 32, 1 or 128, 1 or 128), arrayOf(false, false, true, true)
        )

        val rvAdapter = recyclerView.adapter as EditTextListAdapter

        binding.loginButton.setOnClickListener {
            val typedTexts = rvAdapter.getTypedTexts()
            val newUserData =
                LoginManager.UserData(typedTexts[0], typedTexts[1], typedTexts[2])

            when (userDataValidator.validate(newUserData, typedTexts[3])) {
                0 -> {
                    if (binding.registerCheckBox.isChecked) {
                        loginManager.registerUser(newUserData)
                        this.findNavController()
                            .navigate(R.id.action_registerFragment_to_playerFragment)
                    } else DialogueWindowManager.showAlert(
                        getString(R.string.terms_of_use_alert),
                        requireActivity()
                    )
                }
                1 -> DialogueWindowManager.showAlert(
                    getString(R.string.passwords_not_match_alert),
                    requireActivity()
                )
                2 -> DialogueWindowManager.showAlert(
                    getString(R.string.incorrect_email_alert),
                    requireActivity()
                )
                3 -> DialogueWindowManager.showAlert(
                    getString(R.string.incorrect_login_alert),
                    requireActivity()
                )
            }
        }

        binding.newAccountButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.registerCheckBox.setOnClickListener {
            binding.loginButton.isEnabled = binding.registerCheckBox.isChecked
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}