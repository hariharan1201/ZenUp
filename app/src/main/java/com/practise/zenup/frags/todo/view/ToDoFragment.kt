package com.practise.zenup.frags.todo.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.DocumentSnapshot
import com.practise.zenup.R
import com.practise.zenup.base.AppBaseFragment
import com.practise.zenup.databinding.FragmentToDoBinding
import com.practise.zenup.frags.todo.adopter.ToDoAdopter
import com.practise.zenup.frags.todo.repo.ToDoState
import com.practise.zenup.frags.todo.viewmodel.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDoFragment : AppBaseFragment() {

    private val viewModel: ToDoViewModel by viewModels()
    private lateinit var binding: FragmentToDoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getToDos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            viewModel.todoState.observe(viewLifecycleOwner){ state->
                showProgressBar(state == ToDoState.Loading)
                when(state){
                    is ToDoState.GetToDo -> onGetData(state.todo)
                    ToDoState.Success -> viewModel.getToDos()
                    ToDoState.Deleted -> viewModel.getToDos()
                    else ->{}
                }
            }

            todoListView.layoutManager = LinearLayoutManager(requireContext())

            addTodoBtn.setOnClickListener { alertDialog() }

        }
    }

    private fun alertDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.add_todo_alert_dialog, null)
        val todoField = dialogView.findViewById<AppCompatEditText>(R.id.todo_fld)
        val doneBtn = dialogView.findViewById<MaterialButton>(R.id.add_todo)

        val dialog  = AlertDialog.Builder(requireContext()).setView(dialogView).create()

        doneBtn.setOnClickListener {
            viewModel.setToDo(todoField.text.toString())
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun onCheck(data : DocumentSnapshot) { viewModel.removeToDo(data.id) }

    private fun onGetData(data: MutableList<DocumentSnapshot>) {
        binding.apply {
            if(data.size > 0){
                todoListView.adapter = ToDoAdopter(data){ datum -> onCheck(datum) }
            }
            noTodoInfo.isVisible = data.size == 0
            todoListView.isVisible = data.size > 0
        }
    }

}