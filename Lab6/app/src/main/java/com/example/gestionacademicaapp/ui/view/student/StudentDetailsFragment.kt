package com.example.gestionacademicaapp.ui.view.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gestionacademicaapp.R
import com.example.gestionacademicaapp.data.model.StudentModel
import com.example.gestionacademicaapp.databinding.FragmentStudentDetailsBinding
import com.example.gestionacademicaapp.ui.view.course.CoursesFragment
import kotlinx.android.synthetic.main.nav_fragment_container.*


class StudentDetailsFragment : Fragment() {

    private lateinit var binding: FragmentStudentDetailsBinding
    private lateinit var student: StudentModel
    private var preferredShowedFragment: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentStudentDetailsBinding.inflate(inflater, container, false)

        val studentArg = arguments?.getSerializable("student")
        student = studentArg as StudentModel

        val preferredFragmentArg = arguments?.getSerializable("fragment")
        if(preferredFragmentArg != null ) preferredShowedFragment = preferredFragmentArg as Int

        initListeners()
        initFragment()
        return binding.root
    }

    private fun initListeners() {
        binding.careerBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.career_info -> {
                    (activity as AppCompatActivity).toolbar.title = "Student Information"
                    initInfoFragment()
                    true
                }
                R.id.career_courses -> {
                    (activity as AppCompatActivity).toolbar.title = "Students"
                    initCoursesFragment()
                    true
                }
                else -> false
            }
        }
    }

    private fun initFragment(){
       // preferredShowedFragment = null
        if(preferredShowedFragment != null){
            when(preferredShowedFragment){
                1 -> {
                    (activity as AppCompatActivity).toolbar.title = "Student Information"
                    initInfoFragment()
                }
                2 -> {
                   (activity as AppCompatActivity).toolbar.title = "Students"
                    initCoursesFragment()
                }
            }
        }
        (activity as AppCompatActivity).toolbar.title = "Student Information"
        initInfoFragment()
    }

    private fun initInfoFragment(){
        val bundle = Bundle()
        val fragment = StudentInfoFragment()

        bundle.putSerializable("student", student)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().replace(R.id.career_details_container, fragment).commit()
    }

    private fun initCoursesFragment(){
        val bundle = Bundle()
        val fragment = CoursesFragment()

        bundle.putSerializable("student", student)
        fragment.arguments = bundle


        parentFragmentManager.beginTransaction().replace(R.id.career_details_container, fragment).commit()
    }

}