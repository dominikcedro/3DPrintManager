package com.example.projectmanager.CommentsRecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Comments_RecyclerViewAdapter(var dataSet: ArrayList<CommentModel>) :
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
        viewHolder.commentLikes.text = comment.likes.size.toString()

        viewHolder.commentLikes.setOnClickListener {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId != null) {
                if (comment.likes.contains(userId)) {
                    comment.likes.remove(userId)
                } else {
                    comment.likes.add(userId)
                }

                val db = FirebaseFirestore.getInstance()
                comment.id?.let { id ->
                    db.collection("comments")
                        .document(id)
                        .set(comment)
                        .addOnSuccessListener {
                            viewHolder.commentLikes.text = comment.likes.size.toString()
                        }
                        .addOnFailureListener { e ->
                            // Handle the error
                        }
                }
            }
        }
    }

    override fun getItemCount() = dataSet.size
}