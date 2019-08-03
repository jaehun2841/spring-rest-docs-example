package com.example.restdocs.user.controller

import com.example.restdocs.user.dto.Response
import com.example.restdocs.user.dto.UserDto
import com.example.restdocs.user.repository.User
import com.example.restdocs.user.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/user")
open class UserController(
        val userService: UserService
) {

  @GetMapping("/{userId}")
  fun getUser(@PathVariable(value = "userId") userId: Long): Response<User> {
    val searchUser = userService.search(userId)
    return Response.success(searchUser)
  }

  @PostMapping
  fun createUser(@RequestBody userDto: UserDto): Response<User> {
    val createUser = userService.create(User(name = userDto.name, address = userDto.address, age = userDto.age))
    return Response.success(createUser)
  }


  @PutMapping("/{userId}")
  fun updateUser(@PathVariable("userId") userId: Long,
                 @RequestBody userDto: UserDto): Response<User> {
    val updateUser = userService.update(User(id = userId, name = userDto.name, address = userDto.address, age = userDto.age))
    return Response.success(updateUser)
  }

  @DeleteMapping("/{userId}")
  fun deleteUser(@PathVariable(value = "userId") userId: Long): Response<Unit> {
    userService.delete(userId)
    return Response.success()
  }

  @PostMapping("/{userId}/role/{roleId}")
  fun grantRole(@PathVariable(value = "userId") userId: Long,
                @PathVariable(value = "roleId") roleId: Long): Response<Unit> {
     userService.grantRole(userId = userId, roleId = roleId)
    return Response.success()
  }
}