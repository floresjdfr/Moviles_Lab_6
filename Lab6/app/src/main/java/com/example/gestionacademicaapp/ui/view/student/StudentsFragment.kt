package com.example.gestionacademicaapp.ui.view.student

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
import com.example.gestionacademicaapp.databinding.FragmentStudentsBinding
import com.example.gestionacademicaapp.ui.viewmodel.StudentViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.nav_fragment_container.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentsFragment : Fragment() {

    private lateinit var recyclerViewElement: RecyclerView
    private lateinit var adapter: StudentAdapterRecyclerView

    private val viewModel: StudentViewModel by viewModels()

    private lateinit var binding: FragmentStudentsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentStudentsBinding.inflate(inflater, container, false)

        recyclerViewElement = binding.careerRecyclerview
        recyclerViewElement.layoutManager = LinearLayoutManager(recyclerViewElement.context)
        recyclerViewElement.setHasFixedSize(true)

        initAdapter()
        initListeners()
        initObservers()

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerViewElement)

        return binding.root
    }

    private fun initObservers() {
        viewModel.students.observe(this) {
            adapter.itemsList = it
            adapter.notifyDataSetChanged()
        }

        viewModel.isLoading.observe(this) {
            binding.progress.isVisible = it
        }
    }

    private fun initAdapter() {
        viewModel.getStudents(context!!)
        var nCareerList = viewModel.students.value!!
        adapter = StudentAdapterRecyclerView(nCareerList)
        recyclerViewElement.adapter = adapter
        adapter.setOnClickListener(object: StudentAdapterRecyclerView.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val career = adapter.getAtPosition(position)
                val bundle = Bundle()
                val fragment = StudentDetailsFragment()

                bundle.putSerializable("career", career)
                fragment.arguments = bundle

                activity?.toolbar?.title = "Career Information"
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            }
        })
    }

    private fun initListeners() {
        binding.addCareer.setOnClickListener {
            activity?.toolbar?.title = "Create Career"
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, CreateStudentFragment()).commit()
        }
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
                deleteItem(position)
            } else { //Edit
                editItem(position)
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
                .addSwipeRightBackgroundColor(ContextCompat.getColor(context!!, R.color.primaryLight))
                .addSwipeRightActionIcon(R.drawable.ic_pencil)
                .create()
                .decorate()

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    private fun deleteItem(position: Int) {
        AlertDialog.Builder(context)
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Delete") { _: DialogInterface, _: Int ->
                CoroutineScope(Dispatchers.Main).launch {
                    var itemToDelete = adapter.getAtPosition(position)?.ID!!
//                    val response = viewModel.deleteCareer(itemToDelete)
//                    if (response) {
//                        adapter.deleteAtPosition(position)
//                        adapter.notifyItemRemoved(position)
//                    } else {
//                        adapter.notifyItemChanged(position)
//                    }
                }
            }
            .setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
                adapter.notifyItemChanged(position)
            }
            .create()
            .show()
    }

    private fun editItem(position: Int) {
        AlertDialog.Builder(context)
            .setMessage("Are you sure you want to edit this item?")
            .setPositiveButton("Edit") { _: DialogInterface, _: Int ->
                CoroutineScope(Dispatchers.Main).launch {
                    val itemToEdit = adapter.getAtPosition(position)!!

                    val bundle = Bundle()
                    bundle.putSerializable("career", itemToEdit)
                    val fragment = CreateStudentFragment()
                    fragment.arguments = bundle

                    activity?.toolbar?.title = "Edit Career"
                    parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
                }
            }
            .setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
                adapter.notifyItemChanged(position)
            }
            .create()
            .show()
    }
}