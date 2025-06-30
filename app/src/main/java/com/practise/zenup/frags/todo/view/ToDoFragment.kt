package com.practise.zenup.frags.todo.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.practise.zenup.R
import com.practise.zenup.frags.todo.viewmodel.ToDoViewModel

class ToDoFragment : Fragment() {

    private val viewModel: ToDoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }
}