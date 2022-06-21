package com.example.gestionacademicaapp.ui.view.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gestionacademicaapp.R
import com.example.gestionacademicaapp.data.model.CourseModel
import com.example.gestionacademicaapp.databinding.FragmentCreateCourseBinding
import com.example.gestionacademicaapp.ui.viewmodel.CourseViewModel


class CreateCourseFragment : Fragment() {

    private lateinit var binding: FragmentCreateCourseBinding
    private val viewModel: CourseViewModel by viewModels()
    private var course: CourseModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreateCourseBinding.inflate(inflater, container, false)

        val courseArg = arguments?.getSerializable("course")
        if (courseArg != null)
            course = courseArg as CourseModel

        initObservers()

        if (course != null) { //Edit
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
            createCourse()
            Toast.makeText(context, "Course added!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, CoursesFragment()).commit()
        }
    }

    private fun initEditListeners() {
        binding.createButton.setOnClickListener {
            val response = editCareer()
            if (response) {
                Toast.makeText(context, "Course edited!", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container, CoursesFragment())
                    .commit()
            } else {
                Toast.makeText(context, "An error occurred while editing!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initEditFields() {
        binding.courseDescription.editText?.setText(course?.Description)
        binding.courseCredits.editText?.setText(course?.Credits.toString())
    }

    private fun createCourse() {
        val courseDescription = binding.courseDescription.editText?.text.toString()
        val courseCredits = binding.courseCredits.editText?.text.toString().toInt()

        val course = CourseModel(0, courseDescription, courseCredits)

        viewModel.createCourse(context!!, course)
    }

    private fun editCareer(): Boolean {
        val courseDescription = binding.courseDescription.editText?.text.toString()
        val courseCredits = binding.courseCredits.editText?.text.toString().toInt()

        course?.Description = courseDescription
        course?.Credits = courseCredits

        return viewModel.updateCourse(context!!, course!!)
    }
}