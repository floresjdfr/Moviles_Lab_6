package com.example.gestionacademicaapp.ui.view.student

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.gestionacademicaapp.R
import com.example.gestionacademicaapp.data.model.StudentModel
import kotlinx.android.synthetic.main.template_student.view.*

class StudentAdapterRecyclerView(items: List<StudentModel>) : RecyclerView.Adapter<ViewHolder>() {

    var itemsList: List<StudentModel>? = null
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
        val careerListView = LayoutInflater.from(parent.context).inflate(R.layout.template_student, parent, false)
        val sch = CareerHolder(careerListView, mListener)
        mContext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsList?.get(position)

        holder.itemView.recyclerview_student_name.text = item?.Name
        holder.itemView.recyclerview_student_lastname.text = item?.LastName
    }

    fun getAtPosition(position: Int): StudentModel?{
        return itemsList?.get(position)
    }

    fun deleteAtPosition(position: Int){
        var auxList = ArrayList<StudentModel>()
        var elementToDelete = itemsList?.get(position)
        itemsList?.forEach {
            if(it.ID != elementToDelete?.ID)
                auxList.add((it))
        }
        itemsList = auxList
    }
}