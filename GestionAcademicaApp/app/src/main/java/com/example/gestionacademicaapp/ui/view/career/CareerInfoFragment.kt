package com.example.gestionacademicaapp.ui.view.career

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gestionacademicaapp.databinding.FragmentCareerInfoBinding


class CareerInfoFragment : Fragment() {
    private lateinit var binding: FragmentCareerInfoBinding
    private lateinit var career: CareerModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCareerInfoBinding.inflate(inflater, container, false)

        val careerArg = arguments?.getSerializable("career")
        career = careerArg as CareerModel

        initData()
        return binding.root
    }

    private fun initData() {
        binding.careerCode.editText?.setText(career.Code)
        binding.careerName.editText?.setText(career.CareerName)
        binding.careerTitle.editText?.setText(career.DegreeName)
    }
}