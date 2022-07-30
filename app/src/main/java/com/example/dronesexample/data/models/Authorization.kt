package com.example.dronesexample.data.models

class Authorization (
    var username: String? = null,
    var password: String? = null,
    var refresh_token: String? = null,
    var access_token: String? = null,
    var status: String? = null,
    var roles: List<String>? = null
)