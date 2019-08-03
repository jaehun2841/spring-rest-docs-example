package com.example.restdocs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class RestdocsApplication

fun main(args: Array<String>) {
  runApplication<RestdocsApplication>(*args) {
  }
}
