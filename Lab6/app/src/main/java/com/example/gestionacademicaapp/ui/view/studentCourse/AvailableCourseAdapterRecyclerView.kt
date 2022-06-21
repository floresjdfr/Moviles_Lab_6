package com.example.gestionacademicaapp.ui.view.studentCourse

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.gestionacademicaapp.R
import com.example.gestionacademicaapp.data.model.CourseModel
import com.example.gestionacademicaapp.ui.viewmodel.StudentCoursesViewModel
import com.example.gestionacademicaapp.ui.viewmodel.StudentViewModel
import kotlinx.android.synthetic.main.template_available_course.view.*
import kotlinx.android.synthetic.main.template_course.view.*
import kotlinx.android.synthetic.main.template_course.view.recyclerview_course_credits
import kotlinx.android.synthetic.main.template_course.view.recyclerview_course_description

class AvailableCourseAdapterRecyclerView(items: List<CourseModel>, viewModel: StudentCoursesViewModel) : RecyclerView.Adapter<ViewHolder>() {

    var itemsList: List<CourseModel>? = null
    var viewModel = viewModel

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    private lateinit var mContext: Context
    private lateinit var mListener: OnItemClickListener

    class CareerHolder(itemView: View, listener: OnItemClickListener) : ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    init {
        this.itemsList = items
    }

    fun setOnClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val careerListView = LayoutInflater.from(parent.context).inflate(R.layout.template_available_course, parent, false)
        val sch = CareerHolder(careerListView, mListener)
        mContext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsList?.get(position)

        holder.itemView.recyclerview_course_description.text = item?.Description
        holder.itemView.recyclerview_course_credits.text = item?.Credits.toString()

        if (viewModel.isEnrolledCourse(item!!))
            holder.itemView.recyclerview_course_enrolled.visibility = View.VISIBLE


    }

    fun getAtPosition(position: Int): CourseModel?{
        return itemsList?.get(position)
    }

    fun deleteAtPosition(position: Int){
        var auxList = ArrayList<CourseModel>()
        var elementToDelete = itemsList?.get(position)
        itemsList?.forEach {
            if(it.ID != elementToDelete?.ID)
                auxList.add((it))
        }
        itemsList = auxList
    }
}