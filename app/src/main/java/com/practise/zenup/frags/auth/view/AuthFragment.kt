package com.practise.zenup.frags.auth.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.practise.zenup.R
import com.practise.zenup.base.AppBaseFragment
import com.practise.zenup.databinding.FragmentAuthBinding
import com.practise.zenup.frags.auth.repo.AuthState
import com.practise.zenup.frags.auth.viewmodel.AuthViewModel
import com.practise.zenup.utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : AppBaseFragment() {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentAuthBinding
    private var signUpView : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            viewModel.authState.observe(viewLifecycleOwner) { state ->
                authBtn.isEnabled = state != AuthState.Loading
                when(state){
                    AuthState.Success -> { findNavController().navigate(R.id.action_authFragment_to_homeFragment) }
                    AuthState.Failed -> { showToast(getString(R.string.login_failed)) }
                    else -> {}
                }
            }

            authBtn.setOnClickListener {
                if(signUpView) validateSignUp(emailFld.text.toString(), passwordFld.text.toString(), confirmPasswordFld.text.toString())
                else validateSignIn(emailFld.text.toString(), passwordFld.text.toString())
            }

            signUp.setOnClickListener { handleSignUp() }

        }

    }

    private fun handleSignUp() {
        signUpView = !signUpView
        binding.apply {
            confirmPasswordFld.isVisible = signUpView
            if(signUpView){
                signUpInfo.setText(R.string.account_exits)
                signUp.setText(R.string.login)
                authBtn.setText(R.string.sign_up)
            }else{
                signUpInfo.setText(R.string.don_t_you_have_an_account)
                signUp.setText(R.string.sign_up)
                authBtn.setText(R.string.login)
            }
        }
    }

    private fun validateSignIn(email: String, password: String) {
        binding.apply {
            if(isValidEmail(email) && password.length >= 6){ viewModel.login(email, password) }
            else if(!isValidEmail(email)){ emailFld.error = getString(R.string.invalid_email) }
            else{ passwordFld.error = getString(R.string.invalid_password) }
        }
    }

    private fun validateSignUp(email : String, password : String, confirmPassword: String) {
        binding.apply {
            if(isValidEmail(email) && password == confirmPassword){ viewModel.signup(email, password) }
            else if(!isValidEmail(email)){ emailFld.error = getString(R.string.invalid_email) }
            else{ confirmPasswordFld.error = getString(R.string.password_does_not_match) }
        }
    }

}