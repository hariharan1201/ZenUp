package com.practise.zenup.frags.profile.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser
import com.practise.zenup.R
import com.practise.zenup.base.AppBaseFragment
import com.practise.zenup.databinding.FragmentProfileBinding
import com.practise.zenup.frags.profile.repo.ProfileState
import com.practise.zenup.frags.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : AppBaseFragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            viewModel.profileState.observe(viewLifecycleOwner){ state ->
                when(state){
                    is ProfileState.Success -> bindUserData(state.data)
                    ProfileState.Loading -> showProgressBar(true)
                    ProfileState.LogOut -> logOut()
                    else -> { showProgressBar(false) }
                }
            }

            logoutBtn.setOnClickListener { viewModel.logOut() }
        }

    }

    private fun bindUserData(data : FirebaseUser) {
        binding.apply {
            username.text = data.uid
            email.text = data.email
            showProgressBar(false)
        }
    }

    private fun logOut() { findNavController().navigate(R.id.action_homeFragment_to_authFragment) }

}