package com.example.gestionacademicaapp.ui.view.career

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gestionacademicaapp.R
import com.example.gestionacademicaapp.databinding.FragmentCreateCareerBinding
import com.example.gestionacademicaapp.ui.viewmodel.CareerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateCareerFragment : Fragment() {

    private lateinit var binding: FragmentCreateCareerBinding
    private val viewModel: CareerViewModel by viewModels()
    private var editCareer: CareerModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreateCareerBinding.inflate(inflater, container, false)

        val careerArg = arguments?.getSerializable("career")
        if (careerArg != null)
            editCareer = careerArg as CareerModel

        initObservers()

        if (editCareer != null) { //Edit
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
            CoroutineScope(Dispatchers.Main).launch {
                createCareer()
                Toast.makeText(context, "Career added!", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container, CareersFragment()).commit()
            }
        }
    }

    private fun initEditListeners() {
        binding.createButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                var response = editCareer()
                if (response) {
                    Toast.makeText(context, "Career edited!", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction().replace(R.id.fragment_container, CareersFragment())
                        .commit()
                } else {
                    Toast.makeText(context, "An error occurred while editing!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initEditFields(){
        binding.careerCode.editText?.setText(editCareer?.Code)
        binding.careerName.editText?.setText(editCareer?.CareerName)
        binding.careerTitle.editText?.setText(editCareer?.DegreeName)
    }

    private suspend fun createCareer() {
        val careerCode = binding.careerCode.editText?.text.toString()
        val careerName = binding.careerName.editText?.text.toString()
        val careerTitle = binding.careerTitle.editText?.text.toString()

        val career = CareerModel(0, careerCode, careerName, careerTitle)

        viewModel.createCareer(career)
    }

    private suspend fun editCareer(): Boolean {
        val careerCode = binding.careerCode.editText?.text.toString()
        val careerName = binding.careerName.editText?.text.toString()
        val careerTitle = binding.careerTitle.editText?.text.toString()

        editCareer?.Code = careerCode
        editCareer?.CareerName = careerName
        editCareer?.DegreeName = careerTitle

        return viewModel.updateCareer(editCareer?.ID!!, editCareer!!)
    }

}