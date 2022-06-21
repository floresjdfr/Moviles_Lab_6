package com.example.gestionacademicaapp.ui.view.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gestionacademicaapp.R
import com.example.gestionacademicaapp.data.model.StudentModel
import com.example.gestionacademicaapp.databinding.FragmentCreateStudentBinding
import com.example.gestionacademicaapp.ui.viewmodel.StudentViewModel
import kotlinx.android.synthetic.main.nav_fragment_container.*


class CreateStudentFragment : Fragment() {

    private lateinit var binding: FragmentCreateStudentBinding
    private val viewModel: StudentViewModel by viewModels()
    private var editStudent: StudentModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreateStudentBinding.inflate(inflater, container, false)

        val studentArg = arguments?.getSerializable("student")
        if (studentArg != null)
            editStudent = studentArg as StudentModel

        initObservers()

        if (editStudent != null) { //Edit
            initEditListeners()
            initEditFields()
        } else { //Create
            initCreateListeners()
        }

        return binding.root
    }

    private fun initObservers() {
        viewModel.isLoading.observe(this) {
            binding.progress.isVisible = it
        }
    }

    private fun initCreateListeners() {
        binding.createButton.setOnClickListener {
            createStudents()
            Toast.makeText(context, "Student added!", Toast.LENGTH_SHORT).show()
            activity?.toolbar?.title = "Students"
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, StudentsFragment()).commit()
        }
    }

    private fun initEditListeners() {
        binding.createButton.setOnClickListener {
            val response = editCareer()
            if (response) {
                Toast.makeText(context, "Student edited!", Toast.LENGTH_SHORT).show()
                activity?.toolbar?.title = "Students"
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container, StudentsFragment())
                    .commit()
            } else {
                Toast.makeText(context, "An error occurred while editing!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initEditFields() {
        binding.studentName.editText?.setText(editStudent?.Name)
        binding.studentLastName.editText?.setText(editStudent?.LastName)
        binding.studentAge.editText?.setText(editStudent?.Age!!.toString())
    }

    private fun createStudents() {
        val studentName = binding.studentName.editText?.text.toString()
        val studentLastname = binding.studentLastName.editText?.text.toString()
        val studentAge = binding.studentAge.editText?.text.toString().toInt()

        val student = StudentModel(0, studentName, studentLastname, studentAge, ArrayList())

        viewModel.createStudent(context!!, student)
    }

    private fun editCareer(): Boolean {
        val studentName = binding.studentName.editText?.text.toString()
        val studentLastname = binding.studentLastName.editText?.text.toString()
        val studentAge = binding.studentAge.editText?.text.toString().toInt()

        editStudent?.Name = studentName
        editStudent?.LastName = studentLastname
        editStudent?.Age = studentAge

        return viewModel.updateStudent(context!!, editStudent!!)
    }
}