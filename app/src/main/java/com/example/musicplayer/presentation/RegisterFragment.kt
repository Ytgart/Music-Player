package com.example.musicplayer.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.musicplayer.*
import com.example.musicplayer.data.database.PlayerUser
import com.example.musicplayer.databinding.FragmentRegisterBinding
import com.example.musicplayer.helpers.UserDataValidator
import dev.chrisbanes.insetter.applyInsetter

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var validator: UserDataValidator
    private lateinit var navController: NavController
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        validator = UserDataValidator()
        navController = this.findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

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
        val inputFields = arrayOf(
            binding.loginEditText,
            binding.emailEditText,
            binding.passwordEditText,
            binding.repeatPasswordEditText
        )
        validator.setValidationListeners(inputFields)

        binding.loginButton.setOnClickListener {
            validator.validateFields(inputFields)
            if (validator.hasNoErrors(inputFields)) {
                val data = PlayerUser(
                    binding.loginEditText.text.toString(),
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
                (activity as MainActivity).playerUserDao.insert(data)
                (activity as MainActivity).loginStateRepository.saveLoginState(true)
                navController.navigate(R.id.action_registerFragment_to_mainScreenFragment)
            }
        }

        registerViewModel.buttonState.observe(viewLifecycleOwner, {
            binding.loginButton.isEnabled = it
        })

        binding.newAccountButton.setOnClickListener {
            navController.navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.registerCheckBox.setOnClickListener {
            binding.loginButton.isEnabled = binding.registerCheckBox.isChecked
            registerViewModel.buttonState.postValue(binding.registerCheckBox.isChecked)
        }
    }
}
