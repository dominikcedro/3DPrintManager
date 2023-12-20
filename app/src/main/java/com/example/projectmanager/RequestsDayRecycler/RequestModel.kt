package com.example.projectmanager.RequestsDayRecycler

class RequestModel {
    var username: String? = null
    var subject: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var totalTime: String? = null

    constructor()

    constructor(username: String?, subject: String?, startDate: String?, endDate: String?, totalTime: String?) {
        this.username = username
        this.subject = subject
        this.startDate = startDate
        this.endDate = endDate
        this.totalTime = totalTime
}
}