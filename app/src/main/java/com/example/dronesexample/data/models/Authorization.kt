package com.example.dronesexample.data.models

// identical to data model
// name is key because there's only 1 object

class Authorization (
    var refresh_token: String? = null,
    var access_token: String? = null,
    var status: String? = null,
    var roles: Array<String>? = null
)