package com.example.restdocs.user.service

import com.example.restdocs.user.repository.User
import org.springframework.stereotype.Service

@Service
class EmptyUserService: UserService {
  override fun search(userId: Long): User? = null

  override fun create(user: User): User? = null

  override fun update(user: User): User? = null

  override fun delete(userId: Long){
  }

  override fun grantRole(userId: Long, roleId: Long): User? = null
}