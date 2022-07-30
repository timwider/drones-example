package com.example.dronesexample.data.models

// should be concatenated with data profile,
// name is key (only 1 object)
class Profile(
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var first_name: String? = null,
    var last_name: String? = null,
    var birthdate: String? = null,
    var email_confirmed: Boolean? = null,
    var phone_confirmed: Boolean? = null,
    var photo: String? = null
)