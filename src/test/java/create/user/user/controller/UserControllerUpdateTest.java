package create.user.user.controller;

import java.time.LocalDate;

import create.user.user.controller.data.UserData;
import create.user.user.dto.request.UserCreateOrFullUpdateRequest;
import create.user.user.dto.request.UserPartialUpdateRequest;
import create.user.user.dto.response.UserResponse;
import create.user.user.reposytori.UserRepositorySpring;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerUpdateTest extends UserMockMvcTestBase {

  @Autowired
  private UserRepositorySpring userRepository;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
  }

  @Test
  @SuppressWarnings("MagicNumber")
  public void testFullUpdateUserReturnsOk() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();
    UserResponse response = super.createUser(request);

    String addValue = "value";

    UserCreateOrFullUpdateRequest updateRequest = UserData.getUserCreateOrFullUpdateRequest();

    updateRequest.setEmail(addValue + response.getEmail());
    updateRequest.setFirstName(addValue + response.getFirstName());
    updateRequest.setLastName(addValue + response.getLastName());
    updateRequest.setBirthday(LocalDate.of(1900, 2, 2));
    updateRequest.setAddress(addValue + response.getAddress());
    updateRequest.setPhone("+380444444444");

    Long userId = response.getId();

    MvcResult updateResult = mockMvc.perform(MockMvcRequestBuilders.put("/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    String content = updateResult.getResponse().getContentAsString();

    UserResponse updateResponse = objectMapper.readValue(content, UserResponse.class);

    Assertions.assertEquals(userId, updateResponse.getId());
    Assertions.assertEquals(request.getEmail(), updateResponse.getEmail());
    Assertions.assertEquals(request.getFirstName(), updateResponse.getFirstName());
    Assertions.assertEquals(request.getLastName(), updateResponse.getLastName());
    Assertions.assertEquals(request.getAddress(), updateResponse.getAddress());
    Assertions.assertEquals(request.getBirthday(), updateResponse.getBirthday());
    Assertions.assertEquals(request.getPhone(), updateResponse.getPhone());
    Assertions.assertNotEquals(updateResponse.getCreatedAt(), updateResponse.getModifiedAt());
  }

  @Test
  public void testFullUpdateUserReturnsBadRequest() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();
    UserResponse response = super.createUser(request);

    request.setBirthday(null);

    Long userId = response.getId();

    mockMvc.perform(MockMvcRequestBuilders.put("/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void testPartialUpdateUserReturnsOk() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();
    UserResponse response = super.createUser(request);

    UserPartialUpdateRequest updateRequest = new UserPartialUpdateRequest();

    updateRequest.setFirstName("New name");

    Long userId = response.getId();

    MvcResult updateResult = mockMvc.perform(MockMvcRequestBuilders.patch("/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    String content = updateResult.getResponse().getContentAsString();

    UserResponse updateResponse = objectMapper.readValue(content, UserResponse.class);

    Assertions.assertEquals(updateRequest.getFirstName(), updateResponse.getFirstName());

    Assertions.assertEquals(userId, updateResponse.getId());
    Assertions.assertEquals(response.getEmail(), updateResponse.getEmail());
    Assertions.assertEquals(response.getLastName(), updateResponse.getLastName());
    Assertions.assertEquals(response.getAddress(), updateResponse.getAddress());
    Assertions.assertEquals(response.getBirthday(), updateResponse.getBirthday());
    Assertions.assertEquals(response.getPhone(), updateResponse.getPhone());
    Assertions.assertNotEquals(updateResponse.getCreatedAt(), updateResponse.getModifiedAt());
  }

  @Test
  public void testCheckUpdateUserBirthdayReturnsConflict() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();
    UserResponse response = super.createUser(request);

    Long userId = response.getId();

    LocalDate localDateNou = LocalDate.now();

    //fullUpdate
    request.setBirthday(localDateNou);
    mockMvc.perform(MockMvcRequestBuilders.put("/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isConflict());

    //partialUpdate
    UserPartialUpdateRequest updateRequest = new UserPartialUpdateRequest();

    updateRequest.setBirthday(localDateNou);
    mockMvc.perform(MockMvcRequestBuilders.patch("/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }

  @Test
  public void testCheckUpdateUserIfEmailExistsReturnsConflict() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();
    UserResponse response1 = super.createUser(request);

    String emailUser1 = response1.getEmail();
    String emailUser2 = 1 + emailUser1;

    request.setEmail(emailUser2);
    UserResponse response2 = super.createUser(request);

    Long userId = response2.getId();

    //fullUpdate
    request.setEmail(emailUser1);
    mockMvc.perform(MockMvcRequestBuilders.put("/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isConflict());

    //partialUpdate
    UserPartialUpdateRequest updateRequest = new UserPartialUpdateRequest();

    updateRequest.setEmail(emailUser1);
    mockMvc.perform(MockMvcRequestBuilders.patch("/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateRequest)))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }

}
