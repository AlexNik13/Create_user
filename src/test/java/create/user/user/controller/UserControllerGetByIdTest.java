package create.user.user.controller;

import create.user.user.controller.data.UserData;
import create.user.user.dto.request.UserCreateOrFullUpdateRequest;
import create.user.user.dto.response.UserResponse;
import create.user.user.reposytori.UserRepositorySpring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerGetByIdTest extends UserMockMvcTestBase {

  @Autowired
  private UserRepositorySpring userRepository;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
  }

  @Test
  void getUserByIdReturnsOk() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();

    UserResponse response = super.createUser(request);

    Long userId = response.getId();

    super.mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(request.getEmail()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(request.getFirstName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(request.getLastName()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.birthday").value(request.getBirthday().toString())
        )
        .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(request.getAddress()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(request.getPhone()));
  }

  @Test
  void getUserByIdReturnsNotFound() throws Exception {
    super.mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

}
