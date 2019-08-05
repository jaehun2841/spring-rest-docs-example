package com.example.restdocs.docs

import org.springframework.restdocs.snippet.AbstractDescriptor
import org.springframework.restdocs.snippet.Attributes.key

fun <T : AbstractDescriptor<T>> AbstractDescriptor<T>.remarks(remarks: String): T {
  return this.attributes(key("remarks").value(remarks))
}

fun <T : AbstractDescriptor<T>> AbstractDescriptor<T>.maxLength(length: Int): T {
  return this.attributes(key("maxLength").value(length))
}
