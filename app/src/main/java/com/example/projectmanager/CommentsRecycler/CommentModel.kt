package com.example.projectmanager.CommentsRecycler

class CommentModel {
    var id: String? = null
    var printer: String? = null
    var date: String? = null
    var username: String? = null
    var email: String? = null
    var comment: String? = null
    var likes: MutableList<String> = mutableListOf()

    constructor(id: String?, printer: String?, date: String?, username: String?, email: String?, comment: String?, likes: MutableList<String>?) {
        this.id = id
        this.printer = printer
        this.date = date
        this.username = username
        this.email = email
        this.comment = comment
        this.likes = likes ?: mutableListOf()
    }
}