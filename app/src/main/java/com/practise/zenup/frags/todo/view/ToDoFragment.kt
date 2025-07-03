package com.practise.zenup.frags.todo.view

import android.app.Dialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
    private var todoStates : Boolean = false

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
        viewModel.observeToDo()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            viewModel.todoState.observe(viewLifecycleOwner){ state->
                showProgressBar(state == ToDoState.Loading)
                when(state){
                    is ToDoState.GetToDo -> onGetData(todoStates,state.todo)
//                    ToDoState.Success -> viewModel.getToDos()
                    ToDoState.Deleted -> viewModel.getToDos()
                    else ->{}
                }
            }

            todoListView.layoutManager = LinearLayoutManager(requireContext())

            addTodoBtn.setOnClickListener { alertDialog() }

            swapState.setOnClickListener {
                todoStates = !todoStates
                viewModel.getToDos()
                swapState.text = if(todoStates) "Completed" else "Yet ToDo"
            }

        }
    }

    private fun alertDialog() {
        val dialog  = Dialog(requireContext())
        dialog.setContentView(R.layout.add_todo_alert_dialog)
        dialog.setCancelable(true)
        val doneBtn = dialog.findViewById<MaterialButton>(R.id.add_todo)
        val todoField = dialog.findViewById<TextInputEditText>(R.id.todo_fld)
        val fieldLayout = dialog.findViewById<TextInputLayout>(R.id.todo_fld_layout)

        todoField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {/*null*/}

            override fun onTextChanged(todo: CharSequence?, p1: Int, p2: Int, p3: Int) {
                todo?.toString()?.let {
                    if(it.trim().isEmpty()){
                        fieldLayout.error = getString(R.string.todo_should_not_be_empty)
                    }else{
                        fieldLayout.error = null
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {/*null*/}
        })

        doneBtn.setOnClickListener {
            if(todoField.text?.trim().isNullOrEmpty()){
                fieldLayout.error = getString(R.string.todo_should_not_be_empty)
            }else{
                fieldLayout.error = null
                viewModel.setToDo(todoField.text.toString())
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun onCheck(data : DocumentSnapshot, status: Boolean) { viewModel.removeToDo(data.id, status) }

    private fun onGetData(status : Boolean ,data: MutableList<DocumentSnapshot>) {
        val filteredList = data.filter { snapshot ->
            snapshot.getBoolean("Status") == status
        }.toMutableList()

        binding.apply {
            if(filteredList.size > 0){
                todoListView.adapter = ToDoAdopter(filteredList){ datum, status -> onCheck(datum, status) }
            }
            noTodoInfo.isVisible = filteredList.size == 0
            todoListView.isVisible = filteredList.size > 0
        }
    }

}