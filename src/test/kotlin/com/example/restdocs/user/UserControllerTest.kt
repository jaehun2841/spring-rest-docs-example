package com.example.restdocs.user

import com.example.restdocs.docs.RestApiDocumentUtils.getDocumentRequest
import com.example.restdocs.docs.RestApiDocumentUtils.getDocumentResponse
import com.example.restdocs.docs.maxLength
import com.example.restdocs.docs.remarks
import com.example.restdocs.user.controller.UserController
import com.example.restdocs.user.repository.Role
import com.example.restdocs.user.repository.User
import com.example.restdocs.user.service.UserService
import com.nhaarman.mockito_kotlin.any
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.eq
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@WebMvcTest(UserController::class, secure = false)
@AutoConfigureRestDocs
class UserControllerTest {

  @Autowired
  lateinit var mockMvc: MockMvc

  @MockBean
  lateinit var userService: UserService

  @Test
  fun `user search api docs`() {

    //given
    given(userService.search((eq(1L))))
            .willReturn(User(1, "배달이", 30, "서울특별시 송파구 올림픽로 295"))

    //when
    val resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/user/{userId}", 1)
                    .header("x-api-key", "API-KEY")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
    ).andDo(MockMvcResultHandlers.print())

    //then
    val userIdPathParameter = userIdPathParameter()
    resultActions
            .andExpect(status().isOk)
            .andDo(
                    document(
                            "user-search",
                            getDocumentRequest(),
                            getDocumentResponse(),
                            requestHeaders(*header()),
                            pathParameters(userIdPathParameter()),
                            responseFields(*common())
                                    .andWithPrefix("data.", *user())
                                    .andWithPrefix("data.roles[].", *role())
                            // responseFields(
                            //         beneathPath("data").withSubsectionId("user"),
                            //         *user(),
                            //          subsectionWithPath("roles").description("User Role"))
                    )
            )
  }

  @Test
  fun `user create api docs`() {

    //given
    given(userService.create(any()))
            .willReturn(User(1, "배달이", 30, "서울특별시 송파구 올림픽로 295"))

    //when
    val resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/user")
                    .header("x-api-key", "API-KEY")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(getUserDto())
    ).andDo(MockMvcResultHandlers.print())

    //then
    resultActions
            .andExpect(status().isOk)
            .andDo(
                    document(
                            "user-create",
                            getDocumentRequest(),
                            getDocumentResponse(),
                            requestHeaders(*header()),
                            responseFields(*common())
                                    .andWithPrefix("data.", *user())
                                    .andWithPrefix("data.roles[].", *role())
                    )
            )
  }

  @Test
  fun `user update api docs`() {
    //given
    given(userService.update(any()))
            .willReturn(User(1, "배달이", 30, "서울특별시 송파구 올림픽로 295"))

    //when
    val resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.put("/user/{userId}", 1)
                    .header("x-api-key", "API-KEY")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(getUserDto())
    ).andDo(MockMvcResultHandlers.print())

    //then
    resultActions
            .andExpect(status().isOk)
            .andDo(
                    document(
                            "user-update",
                            getDocumentRequest(),
                            getDocumentResponse(),
                            requestHeaders(*header()),
                            pathParameters(userIdPathParameter()),
                            responseFields(*common())
                                    .andWithPrefix("data.", *user())
                                    .andWithPrefix("data.roles[].", *role())
                    )
            )
  }

  @Test
  fun `user delete api docs`() {
    //given

    //when
    val resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.delete("/user/{userId}", 1)
                    .header("x-api-key", "API-KEY")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
    ).andDo(MockMvcResultHandlers.print())

    //then
    resultActions
            .andExpect(status().isOk)
            .andDo(
                    document(
                            "user-delete",
                            getDocumentRequest(),
                            getDocumentResponse(),
                            requestHeaders(*header()),
                            pathParameters(userIdPathParameter()),
                            responseFields(*common())
                    )
            )
  }

  @Test
  fun `grant role to user api docs`() {
    //given
    given(userService.grantRole(anyLong(), anyLong()))
            .willReturn(
                    User(1, "배달이", 30, "서울특별시 송파구 올림픽로 295", mutableListOf(Role(1, "ROLE_DEFAULT"))))

    //when
    val resultActions = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/user/{userId}/role/{roleId}", 1, 1)
                    .header("x-api-key", "API-KEY")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
    ).andDo(MockMvcResultHandlers.print())

    //then
    resultActions
            .andExpect(status().isOk)
            .andDo(
                    document(
                            "user-grant-role",
                            getDocumentRequest(),
                            getDocumentResponse(),
                            requestHeaders(*header()),
                            pathParameters(userIdPathParameter(), roleIdPathParameter()),
                            responseFields(*common())
                                    .andWithPrefix("data.", *user())
                                    .andWithPrefix("data.roles[].", *role())
                    )
            )
  }

  private fun getUserDto(): String {
    return """
      {
        "name": "배달이",
        "age": 30,
        "address": "서울특별시 송파구 올림픽로 295"
      }
    """.trimIndent()
  }

  private fun header(): Array<HeaderDescriptor> {
    return arrayOf(headerWithName("x-api-key").description("Api Key"))
  }

  private fun userIdPathParameter(): ParameterDescriptor {
    return parameterWithName("userId").description("USER ID").maxLength(10).remarks("User ID가 없는 경우는 먼저 생성하세")
  }

  private fun roleIdPathParameter(): ParameterDescriptor {
    return parameterWithName("roleId").description("ROLE ID")
  }

  private fun common(): Array<FieldDescriptor> {
    return arrayOf(
            fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
            subsectionWithPath("error").type(JsonFieldType.OBJECT).description("에러 Data").optional(),
            subsectionWithPath("data").type(JsonFieldType.OBJECT).description("응답 Data").optional()
    )
  }

  private fun user(): Array<FieldDescriptor> {
    return arrayOf(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("User ID"),
            fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
            fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
            fieldWithPath("address").type(JsonFieldType.STRING).description("주소")
    )
  }

  private fun role(): Array<FieldDescriptor> {
    return arrayOf(
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("Role ID").optional(),
            fieldWithPath("name").type(JsonFieldType.STRING).description("Role명").optional()
    )
  }
}