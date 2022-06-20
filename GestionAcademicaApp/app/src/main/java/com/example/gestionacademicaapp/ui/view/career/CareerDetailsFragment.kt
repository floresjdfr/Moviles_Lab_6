package com.example.gestionacademicaapp.ui.view.career

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gestionacademicaapp.R
import com.example.gestionacademicaapp.databinding.FragmentCareerDetailsBinding
import com.example.gestionacademicaapp.ui.view.course.CoursesFragment
import kotlinx.android.synthetic.main.nav_fragment_container.*


class CareerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCareerDetailsBinding
    private lateinit var career: CareerModel
    private var preferredShowedFragment: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCareerDetailsBinding.inflate(inflater, container, false)

        val careerArg = arguments?.getSerializable("career")
        career = careerArg as CareerModel

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
                    (activity as AppCompatActivity).toolbar.title = "Career Information"
                    initInfoFragment()
                    true
                }
                R.id.career_courses -> {
                    (activity as AppCompatActivity).toolbar.title = "Courses"
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
                    (activity as AppCompatActivity).toolbar.title = "Career Information"
                    initInfoFragment()
                }
                2 -> {
                   (activity as AppCompatActivity).toolbar.title = "Courses"
                    initCoursesFragment()
                }
            }
        }
        (activity as AppCompatActivity).toolbar.title = "Career Information"
        initInfoFragment()
    }

    private fun initInfoFragment(){
        val bundle = Bundle()
        val fragment = CareerInfoFragment()

        bundle.putSerializable("career", career)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().replace(R.id.career_details_container, fragment).commit()
    }

    private fun initCoursesFragment(){
        val bundle = Bundle()
        val fragment = CoursesFragment()

        bundle.putSerializable("career", career)
        fragment.arguments = bundle


        parentFragmentManager.beginTransaction().replace(R.id.career_details_container, fragment).commit()
    }

}