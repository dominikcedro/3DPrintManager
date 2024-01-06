package com.example.projectmanager.RequestsDayRecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R

class Requests_RecyclerViewAdapter(var dataSet: ArrayList<RequestModel>,
    private val listener: OnItemClickListener) :
    RecyclerView.Adapter<Requests_RecyclerViewAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
            val requestSubject: TextView = view.findViewById(R.id.tvSubject)
            val requestStartDate: TextView = view.findViewById(R.id.tvRequestStartt)
            val requestEndDate: TextView = view.findViewById(R.id.tvRequestEnd)
            val detailsButton: Button = view.findViewById<Button>(R.id.detailsButton)
        init{
            detailsButton.setOnClickListener(this)
        }
            override fun onClick(p0: View?) {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }}

        }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_request, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount()=dataSet.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val request = dataSet[position]
        holder.requestSubject.text = request.subject
        holder.requestStartDate.text = request.startDate
        holder.requestEndDate.text = request.endDate
        holder.detailsButton.text = "More details"
    }
}