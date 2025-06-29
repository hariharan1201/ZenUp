package com.practise.zenup.frags.splash.view

import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.practise.zenup.R
import com.practise.zenup.databinding.FragmentSplashBinding
import com.practise.zenup.frags.splash.repo.SplashState
import com.practise.zenup.frags.splash.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAuthStatus();
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.splashState.observe(viewLifecycleOwner) {
            state -> when(state){
                SplashState.LogIn -> android.os.Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_splashFragment_to_authFragment)
                }, 1500)
                SplashState.LoggedIn -> android.os.Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }, 1500)
            }
        }
    }
}