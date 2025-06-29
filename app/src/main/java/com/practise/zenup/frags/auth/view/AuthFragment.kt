package com.practise.zenup.frags.auth.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.practise.zenup.R
import com.practise.zenup.databinding.FragmentAuthBinding
import com.practise.zenup.frags.auth.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentAuthBinding
    private var signUpView : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
}