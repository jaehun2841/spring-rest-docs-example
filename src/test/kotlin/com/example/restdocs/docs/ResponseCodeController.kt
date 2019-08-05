package com.example.restdocs.docs

import com.example.restdocs.user.dto.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ResponseCodeController {

  @GetMapping("/response-code")
  fun getResponseCode(): Response<Unit> {
    return Response.success()
  }
}