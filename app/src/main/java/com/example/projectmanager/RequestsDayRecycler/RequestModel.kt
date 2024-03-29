package com.example.projectmanager.RequestsDayRecycler

import com.example.projectmanager.DateTimeOperation.DateTime
import com.google.firebase.Timestamp

class RequestModel {
    var author: String? = null
    var email: String? = null
    var subject: String? = null
    var startDate: String? = null
    var startTime: String? = null
    var endDate: String? = null
    var endTime: String? = null
    var filament: String? = null
    var startDateTime: Any? = null
    var endDateTime: Any? = null
    var postDate: Timestamp? = null
    var startTimestamp: Timestamp? = null
    var endTimestamp: Timestamp? = null

    constructor(
        author: String?,email:String?, subject: String?, startDate: String?, endDate: String?,
        startTime: String?, endTime: String?, filament: String?, startDateTime: Any?,
        endDateTime: Any?, postDate: Timestamp?, startTimestamp: Timestamp?, endTimestamp: Timestamp?
    ) {
        this.author = author
        this.email = email
        this.subject = subject
        this.startDate = startDate
        this.endDate = endDate
        this.startTime = startTime
        this.endTime = endTime
        this.filament = filament
        this.startDateTime = startDateTime
        this.endDateTime = endDateTime
        this.postDate = postDate
        this.startTimestamp = startTimestamp
        this.endTimestamp = endTimestamp

    }
}