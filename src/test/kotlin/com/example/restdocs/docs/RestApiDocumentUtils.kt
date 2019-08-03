package com.example.restdocs.docs

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris

object RestApiDocumentUtils {

  fun getDocumentRequest(): OperationRequestPreprocessor {
    return Preprocessors.preprocessRequest(
            modifyUris()
              .scheme("http")
              .host("user.api.com")
              .removePort(),
            Preprocessors.prettyPrint()
    )
  }

  fun getDocumentResponse(): OperationResponsePreprocessor {
    return Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
  }
}