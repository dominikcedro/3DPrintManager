package com.example.projectmanager.Firestore

import android.widget.Toast
import com.example.projectmanager.RegisterActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireStoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()


    fun registerUserFS(activity: RegisterActivity, userInfo: User){

        mFireStore.collection("usersData")
            .document(userInfo.email)
            .set(userInfo, SetOptions.merge())
}
    fun createComment(activity: RegisterActivity, commentInfo: CommentData){

        mFireStore.collection("comments")
            .add(commentInfo)
            .addOnSuccessListener{documentReference ->
                Toast.makeText(activity, "Comment added successfully", Toast.LENGTH_SHORT).show()
            }
    }
}