package com.example.gestionacademicaapp.ui.view.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gestionacademicaapp.data.model.StudentModel
import com.example.gestionacademicaapp.data.schemas.Student
import com.example.gestionacademicaapp.databinding.FragmentStudentInfoBinding


class StudentInfoFragment : Fragment() {
    private lateinit var binding: FragmentStudentInfoBinding
    private lateinit var student: StudentModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentStudentInfoBinding.inflate(inflater, container, false)

        val studentArg = arguments?.getSerializable("student")
        student = studentArg as StudentModel

        initData()
        return binding.root
    }

    private fun initData() {
        binding.careerCode.editText?.setText(student.Name)
        binding.careerName.editText?.setText(student.Name)
        binding.careerTitle.editText?.setText(student.Name)
    }
}