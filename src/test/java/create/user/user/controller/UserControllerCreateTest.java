package create.user.user.controller;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import create.user.user.controller.data.UserData;
import create.user.user.dto.request.UserCreateOrFullUpdateRequest;
import create.user.user.reposytori.UserRepositorySpring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerCreateTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepositorySpring userRepository;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
  }

  @Test
  void createUserReturnsCreated() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();

    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
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
  void createUserWithInvalidDataReturnsBadRequest() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();

    request.setFirstName(null);

    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void createUserValidAgeReturnsConflict() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();

    request.setBirthday(LocalDate.now());

    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }

  @Test
  void createUserSameEmailReturnsConflict() throws Exception {
    UserCreateOrFullUpdateRequest request = UserData.getUserCreateOrFullUpdateRequest();

    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isCreated());

    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }

}
