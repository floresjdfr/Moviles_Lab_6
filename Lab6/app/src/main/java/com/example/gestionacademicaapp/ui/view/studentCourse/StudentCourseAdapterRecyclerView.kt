package com.example.gestionacademicaapp.ui.view.studentCourse

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.gestionacademicaapp.R
import com.example.gestionacademicaapp.data.model.StudentCourseModel
import kotlinx.android.synthetic.main.template_course.view.*

class StudentCourseAdapterRecyclerView(items: List<StudentCourseModel>) : RecyclerView.Adapter<ViewHolder>() {

    var itemsList: List<StudentCourseModel>? = null

    private lateinit var mContext: Context

    class CareerHolder(itemView: View) : ViewHolder(itemView)

    init {
        this.itemsList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val careerListView = LayoutInflater.from(parent.context).inflate(R.layout.template_available_course, parent, false)
        val sch = CareerHolder(careerListView)
        mContext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsList?.get(position)

        holder.itemView.recyclerview_course_description.text = item?.Course?.Description
        holder.itemView.recyclerview_course_credits.text = item?.Course?.Credits.toString()


    }

    fun getAtPosition(position: Int): StudentCourseModel? {
        return itemsList?.get(position)
    }

    fun deleteAtPosition(position: Int) {
        var auxList = ArrayList<StudentCourseModel>()
        var elementToDelete = itemsList?.get(position)
        itemsList?.forEach {
            if (it.ID != elementToDelete?.ID)
                auxList.add((it))
        }
        itemsList = auxList
    }
}