package com.example.restdocs.docs

import org.springframework.http.MediaType
import org.springframework.restdocs.operation.Operation
import org.springframework.restdocs.payload.AbstractFieldsSnippet
import org.springframework.restdocs.payload.FieldDescriptor

class ResponseCodeSnippet(
        name: String,
        descriptors: MutableList<FieldDescriptor>,
        attributes: Map<String, Any>,
        ignoreUndocumentedFields: Boolean
) : AbstractFieldsSnippet(name, descriptors, attributes, ignoreUndocumentedFields) {

  override fun getContentType(operation: Operation): MediaType? {
    return operation.response.headers.contentType
  }

  override fun getContent(operation: Operation): ByteArray {
    return operation.response.content
  }
}
