package com.example.gestionacademicaapp.ui.view.studentCourse

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionacademicaapp.R
import com.example.gestionacademicaapp.data.model.StudentModel
import com.example.gestionacademicaapp.databinding.FragmentStudentCoursesBinding
import com.example.gestionacademicaapp.ui.view.course.CourseAdapterRecyclerView
import com.example.gestionacademicaapp.ui.viewmodel.StudentCoursesViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.nav_fragment_container.*

class StudentCoursesFragment : Fragment() {
    private lateinit var recyclerViewElement: RecyclerView
    private lateinit var adapter: StudentCourseAdapterRecyclerView
    private lateinit var student: StudentModel
    private val viewModel: StudentCoursesViewModel by viewModels()
    private lateinit var binding: FragmentStudentCoursesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        student = arguments?.getSerializable("student") as StudentModel
        viewModel.student.value = student
        binding = FragmentStudentCoursesBinding.inflate(inflater, container, false)

        recyclerViewElement = binding.courseRecyclerview
        recyclerViewElement.layoutManager = LinearLayoutManager(recyclerViewElement.context)
        recyclerViewElement.setHasFixedSize(true)

        initAdapter()
        initObservers()

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerViewElement)

        return binding.root
    }

    private fun initObservers() {
        viewModel.studentCourses.observe(this) {
            adapter.itemsList = it
            adapter.notifyDataSetChanged()
        }
        viewModel.isLoading.observe(this) {
            binding.progress.isVisible = it
        }
    }

    private fun initAdapter() {
        viewModel.getStudentCourses(context!!)
        val nCareerList = viewModel.studentCourses.value!!
        adapter = StudentCourseAdapterRecyclerView(nCareerList)
        recyclerViewElement.adapter = adapter

    }

    private val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            if (direction == ItemTouchHelper.LEFT) { //Delete
                unrollItem(position)
            } else { //Nothing
                adapter.notifyItemChanged(position)
            }
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean,
        ) {
            RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(context!!, R.color.secondaryDark))
                .addSwipeLeftActionIcon(R.drawable.ic_trash)
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    private fun unrollItem(position: Int) {
        AlertDialog.Builder(context)
            .setMessage("Are you sure you unroll this course?")
            .setPositiveButton("Delete") { _: DialogInterface, _: Int ->
                val itemToDelete = adapter.getAtPosition(position)
                val response = viewModel.unrollCourse(context!!, itemToDelete!!)
                if (response) {
                    adapter.deleteAtPosition(position)
                    adapter.notifyItemRemoved(position)
                } else {
                    adapter.notifyItemChanged(position)
                }
            }
            .setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
                adapter.notifyItemChanged(position)
            }
            .create()
            .show()
    }
}