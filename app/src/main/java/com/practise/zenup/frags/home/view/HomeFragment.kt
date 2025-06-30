package com.practise.zenup.frags.home.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.replace
import com.practise.zenup.R
import com.practise.zenup.databinding.FragmentHomeBinding
import com.practise.zenup.frags.home.viewmodel.HomeViewModel
import com.practise.zenup.frags.profile.view.ProfileFragment
import com.practise.zenup.frags.remainder.view.ReminderFragment
import com.practise.zenup.frags.todo.view.ToDoFragment

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadPage(ToDoFragment())

        binding.apply {
            bottomNavBar.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.todo_page -> loadPage(ToDoFragment())
                    R.id.reminder_page -> loadPage(ReminderFragment())
                    R.id.profile_page -> loadPage(ProfileFragment())
                }
                true
            }
        }
    }

    private fun loadPage(fragment: Fragment){
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.home_frag_container, fragment)?.commit()
    }
}