package com.example.restdocs.docs

import com.example.restdocs.user.controller.ResponseCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.snippet.Attributes
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*
import java.util.stream.Collectors

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@WebMvcTest(ResponseCodeController::class, secure = false)
@AutoConfigureRestDocs
class ResponseCodeDocs {

  @Autowired
  lateinit var mockMvc: MockMvc

  @Test
  fun `build response code snippet`() {

    mockMvc.perform(get("/response-code")
            .accept(MediaType.APPLICATION_JSON)
    ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andDo(
                    document(
                            "common",
                            responseCodeFields(
                                    "response-code", //{name}-fields.snippet 이라는 파일명으로 생성
                                    Attributes.attributes(Attributes.key("title").value("공통 응답 코드")),
                                    *convertResponseCodeToFieldDescriptor(ResponseCode.values())
                            )
                    )
            )
  }

  fun responseCodeFields(
          name: String,
          attributes: Map<String, Any>,
          vararg descriptors: FieldDescriptor
  ): ResponseCodeSnippet {
    return ResponseCodeSnippet(name, mutableListOf(*descriptors), attributes, true)
  }

  private fun convertResponseCodeToFieldDescriptor(enumTypes: Array<ResponseCode>): Array<FieldDescriptor> {
    return Arrays.stream(enumTypes)
            .map {
              fieldWithPath(it.code.toString()).type(JsonFieldType.NUMBER).description(it.message).optional()
            }
            .collect(Collectors.toList())
            .toTypedArray()
  }
}

