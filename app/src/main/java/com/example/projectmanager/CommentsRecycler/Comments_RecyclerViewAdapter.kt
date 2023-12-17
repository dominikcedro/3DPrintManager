package com.example.projectmanager.CommentsRecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R

class Comments_RecyclerViewAdapter(private val dataSet: ArrayList<CommentModel>) :
    RecyclerView.Adapter<Comments_RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val commentAuthor: TextView = view.findViewById(R.id.commentAuthor)
        val commentDate: TextView = view.findViewById(R.id.commentDate)
        val commentBody: TextView = view.findViewById(R.id.commentBody)
        val commentLikes: TextView = view.findViewById(R.id.commentLikes)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_view_row, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val comment = dataSet[position]
        viewHolder.commentAuthor.text = comment.username
        viewHolder.commentDate.text = comment.date
        viewHolder.commentBody.text = comment.comment
        viewHolder.commentLikes.text = comment.likes?.toString()
    }

    override fun getItemCount() = dataSet.size
}