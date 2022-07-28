package com.example.dronesexample.models

class Authorization(
    var refresh_token: String? = null,
    var access_token: String? = null,
    var status: String? = null,
    var roles: Array<String>? = null
)