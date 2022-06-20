package com.example.gestionacademicaapp.ui.view.course

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.gestionacademicaapp.R
import com.example.gestionacademicaapp.data.model.CourseModel
import kotlinx.android.synthetic.main.template_career.view.*

class CourseAdapterRecyclerView(items: List<CourseModel>) : RecyclerView.Adapter<ViewHolder>() {

    var itemsList: List<CourseModel>? = null
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    private lateinit var mContext: Context
    private lateinit var mListener: OnItemClickListener

    class CourseHolder(itemView: View, listener: OnItemClickListener) : ViewHolder(itemView){
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
        val courseListView = LayoutInflater.from(parent.context).inflate(R.layout.template_career, parent, false)
        val sch = CourseHolder(courseListView, mListener)
        mContext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return itemsList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsList?.get(position)

        holder.itemView.recyclerview_career_id.text = item?.Code
        holder.itemView.recyclerview_career_name.text = item?.Name
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