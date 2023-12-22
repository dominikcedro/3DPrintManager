package com.example.projectmanager.RequestsDayRecycler

class RequestModel {
//    var username: String? = null
    var subject: String? = null
    var startDate: String? = null
    var endDate: String? = null

    constructor(){}
    constructor(subject: String?, startDate: String?, endDate: String?) {
        this.subject = subject
        this.startDate = startDate
        this.endDate = endDate
}
}