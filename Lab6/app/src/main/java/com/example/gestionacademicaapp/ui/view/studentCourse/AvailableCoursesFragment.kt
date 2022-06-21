package com.example.gestionacademicaapp.ui.view.studentCourse

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionacademicaapp.data.model.StudentModel
import com.example.gestionacademicaapp.databinding.FragmentAvailableCoursesBinding
import com.example.gestionacademicaapp.ui.view.course.CourseAdapterRecyclerView
import com.example.gestionacademicaapp.ui.viewmodel.StudentCoursesViewModel

class AvailableCoursesFragment : Fragment() {

    private lateinit var recyclerViewElement: RecyclerView
    private lateinit var adapter: CourseAdapterRecyclerView
    private lateinit var student: StudentModel
    private val viewModel: StudentCoursesViewModel by viewModels()
    private lateinit var binding: FragmentAvailableCoursesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAvailableCoursesBinding.inflate(inflater, container, false)

        student = arguments?.getSerializable("student") as StudentModel

        recyclerViewElement = binding.courseRecyclerview
        recyclerViewElement.layoutManager = LinearLayoutManager(recyclerViewElement.context)
        recyclerViewElement.setHasFixedSize(true)

        initAdapter()
        initObservers()

        return binding.root
    }

    private fun initObservers() {
        viewModel.availableCourses.observe(this) {
            adapter.itemsList = it
            adapter.notifyDataSetChanged()
        }

        viewModel.isLoading.observe(this) {
            binding.progress.isVisible = it
        }
    }

    private fun initAdapter() {
        viewModel.getCourses(context!!)
        val nCareerList = viewModel.availableCourses.value!!
        adapter = CourseAdapterRecyclerView(nCareerList)
        recyclerViewElement.adapter = adapter
        adapter.setOnClickListener(object : CourseAdapterRecyclerView.OnItemClickListener {
            override fun onItemClick(position: Int) {
                AlertDialog.Builder(context)
                    .setMessage("Are you sure you want to enroll this course?")
                    .setPositiveButton("Enroll") { _: DialogInterface, _: Int ->
                        val course = adapter.getAtPosition(position)

                        viewModel.createStudentCourse(context!!, student, course!!)

                        Toast.makeText(context!!, "Course enrolled", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
                        adapter.notifyItemChanged(position)
                    }
                    .create()
                    .show()
            }
        })
    }
}