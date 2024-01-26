package com.example.projectmanager.RequestsDayRecycler

import android.content.ContextWrapper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.DatePickFragment.RequestDetailsFragment
import com.example.projectmanager.R

class Requests_RecyclerViewAdapter(var dataSet: ArrayList<RequestModel>,
    private val listener: OnItemClickListener) :
    RecyclerView.Adapter<Requests_RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val requestSubject: TextView = view.findViewById(R.id.tvSubject)
        val requestStartDate: TextView = view.findViewById(R.id.tvRequestStartt)
        val requestEndDate: TextView = view.findViewById(R.id.tvRequestEnd)
        val detailsButton: Button = view.findViewById<Button>(R.id.detailsButton)
        val requestAuthor: TextView = view.findViewById(R.id.tvAuthor)

        init {
            detailsButton.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_request, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val request = dataSet[position]
        holder.requestSubject.text = request.subject
        holder.requestStartDate.text = request.startDate
        holder.requestEndDate.text = request.endDate
        holder.detailsButton.text = "More details"
        holder.requestAuthor.text = request.author
        holder.detailsButton.setOnClickListener {

            val bundle = Bundle().apply {
                putString("subject", request.subject)
                putString("author", request.author)
                putString("startDate", request.startDate)
                putString("endDate", request.endDate)
                putString("startTime", request.startTime)
                putString("endTime", request.endTime)
                putString("filament", request.filament)
            }

            val fragment = RequestDetailsFragment().apply {
                arguments = bundle
            }
            var context = it.context
            while (context is ContextWrapper) {
                if (context is AppCompatActivity) {
                    context.supportFragmentManager.beginTransaction()
                        .replace(R.id.detailsFragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit()
                    break
                }
                context = context.baseContext
            }
        }
    }
}
