package com.example.projectmanager.RequestsDayRecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R

class Requests_RecyclerViewAdapter(var dataSet: ArrayList<RequestModel>) :
    RecyclerView.Adapter<Requests_RecyclerViewAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val requestSubject: TextView = view.findViewById(R.id.tvSubject)
            val requestStartDate: TextView = view.findViewById(R.id.tvRequestStartt)
            val requestEndDate: TextView = view.findViewById(R.id.tvRequestEnd)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val request = dataSet[position]
        holder.requestSubject.text = request.subject
        holder.requestStartDate.text = request.startDate
        holder.requestEndDate.text = request.endDate
    }
}