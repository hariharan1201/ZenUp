package com.practise.zenup.frags.auth.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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

            emailFld.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { /*null*/ }
                override fun onTextChanged(email: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(emailFldLayout.error != null){
                        email?.toString()?.let { validateEmail(it) }
                    }
                }
                override fun afterTextChanged(p0: Editable?) {/*null*/}
            })

            passwordFld.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {/*null*/}
                override fun onTextChanged(password: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(passwordFldLayout.error != null) {
                        password?.toString()?.let { validatePassword(it) }
                    }
                }
                override fun afterTextChanged(p0: Editable?) {/*null*/}
            })

            confirmPasswordFld.addTextChangedListener(object  : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {/*null*/}
                override fun onTextChanged(confirmPassword: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if(confirmPasswordFldLayout.error != null){
                        confirmPassword?.toString()?.let { validatePassword(it) }
                    }
                }
                override fun afterTextChanged(p0: Editable?) {/*null*/}
            })

        }

    }

    private fun handleSignUp() {
        hideKeyboard(requireView())
        filedReset()
        signUpView = !signUpView
        binding.apply {
            confirmPasswordFldLayout.isVisible = signUpView
            passwordFld.imeOptions = if (signUpView) EditorInfo.IME_ACTION_NEXT else EditorInfo.IME_ACTION_DONE
            confirmPasswordFld.imeOptions = EditorInfo.IME_ACTION_DONE
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

    private fun filedReset() {
        binding.apply {
            emailFldLayout.error = null
            passwordFldLayout.error = null
            confirmPasswordFldLayout.error = null
        }
    }

    private fun validateSignIn(email: String, password: String) {
        validateEmail(email)
        validatePassword(password)
        binding.apply {
            if(emailFldLayout.error == null && passwordFldLayout.error == null)
                viewModel.login(email, password)
        }
    }

    private fun validateEmail(email: String) {
        binding.apply {
            emailFldLayout.error = if(isValidEmail(email)) null else getString(R.string.invalid_email)
        }
    }

    private fun validatePassword(password: String, confirmPassword: String = "", isConfirmPassword: Boolean = false) {
        val size = password.length >= 6
        binding.apply {
            if(isConfirmPassword)
                confirmPasswordFldLayout.error = if (password == confirmPassword) null else getString(R.string.password_does_not_match)
            else
                passwordFldLayout.error = if(size) null else getString(R.string.invalid_password)
        }
    }

    private fun validateSignUp(email : String, password : String, confirmPassword: String) {
        validateEmail(email)
        validatePassword(password, confirmPassword, isConfirmPassword = true)
        binding.apply {
            if(isValidEmail(email) && password == confirmPassword){ viewModel.signup(email, password) }
            else{ confirmPasswordFldLayout.error = getString(R.string.password_does_not_match) }
        }
    }

}