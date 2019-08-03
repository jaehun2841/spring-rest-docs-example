package com.example.restdocs.user.repository

data class User(
        val id: Long? = null,
        val name: String,
        val age: Int,
        val address: String,
        var roles: MutableList<Role> = mutableListOf()
)