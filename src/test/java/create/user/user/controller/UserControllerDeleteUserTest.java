package create.user.user.controller;

import create.user.user.controller.data.UserData;
import create.user.user.dto.response.UserResponse;
import create.user.user.reposytori.UserRepositorySpring;
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
class UserControllerDeleteUserTest extends UserMockMvcTestBase {

  @Autowired
  private UserRepositorySpring userRepository;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
  }

  @Test
  public void testDeleteUser() throws Exception {
    UserResponse userResponse = super.createUser(UserData.getUserCreateOrFullUpdateRequest());

    Long userId = userResponse.getId();

    mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userId))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + userId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userId))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

}
