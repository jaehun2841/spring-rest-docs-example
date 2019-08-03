package com.example.restdocs.user.service

import com.example.restdocs.user.repository.User

interface UserService {

  fun search(userId: Long): User?
  fun create(user: User): User?
  fun update(user: User): User?
  fun delete(userId: Long)
  fun grantRole(userId: Long, roleId: Long): User?
}
