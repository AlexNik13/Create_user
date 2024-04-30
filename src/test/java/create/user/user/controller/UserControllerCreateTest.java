package create.user.user.controller;

import java.time.LocalDate;

import create.user.user.controller.data.UserData;
import create.user.user.dto.request.UserCreateOrFullUpdateRequest;
import create.user.user.dto.response.UserResponse;
import create.user.user.reposytori.UserRepositorySpring;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerCreateTest extends UserMockMvcTestBase {

  @Autowired
  private UserRepositorySpring userRepository;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
  }

  @Test
  void createUserReturnsCreated() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();

    UserResponse response = super.createUser(request);

    Assertions.assertEquals(request.getEmail(), response.getEmail());
    Assertions.assertEquals(request.getFirstName(), response.getFirstName());
    Assertions.assertEquals(request.getLastName(), response.getLastName());
    Assertions.assertEquals(request.getAddress(), response.getAddress());
    Assertions.assertEquals(request.getBirthday(), response.getBirthday());
    Assertions.assertEquals(request.getPhone(), response.getPhone());

    Assertions.assertNotNull(response.getCreatedAt());
    Assertions.assertNotNull(response.getModifiedAt());
    Assertions.assertNotNull(response.getId());
  }

  @Test
  void createUserWithInvalidDataReturnsBadRequest() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();

    request.setFirstName(null);

    super.mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(super.objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void createUserValidAgeReturnsConflict() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();

    request.setBirthday(LocalDate.now());

    super.mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(super.objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }

  @Test
  void createUserSameEmailReturnsConflict() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();

    super.createUser(request);

    super.mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(super.objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }

}
