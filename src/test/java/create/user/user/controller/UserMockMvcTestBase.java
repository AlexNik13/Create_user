package create.user.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import create.user.user.dto.request.UserCreateOrFullUpdateRequest;
import create.user.user.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public abstract class UserMockMvcTestBase {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  protected UserResponse createUser(UserCreateOrFullUpdateRequest request) throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andReturn();

    String content = result.getResponse().getContentAsString();

    return objectMapper.readValue(content, UserResponse.class);
  }

}
