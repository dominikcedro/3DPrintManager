package com.example.projectmanager.CommentsRecycler

class CommentModel {
    var printer: String? = null
    var date: String? = null
    var username: String? = null
    var email: String? = null
    var comment: String? = null
    var likes: Int? = null

    constructor() {}
    constructor(printer: String?, date: String?, username: String?, email: String?, comment: String?, likes: Int?) {
        this.printer = printer
        this.date = date
        this.username = username
        this.email = email
        this.comment = comment
        this.likes = likes
    }
}