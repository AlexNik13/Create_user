package create.user.user.controller;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import create.user.user.dto.request.UserCreateOrFullUpdateRequest;
import create.user.user.reposytori.UserRepositorySpring;
import create.user.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTestCreate {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepositorySpring userRepository;

  @Autowired
  private UserService userService;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
  }

  @Test
  void createUser() throws Exception {
    UserCreateOrFullUpdateRequest request = getUserCreateOrFullUpdateRequest();

    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(request.getEmail()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(request.getFirstName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(request.getLastName()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.birthday").value(request.getBirthday().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(request.getAddress()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(request.getPhone()));
  }

  @Test
  void createUserWithInvalidDataReturnsBadRequest() throws Exception {
    UserCreateOrFullUpdateRequest request = getUserCreateOrFullUpdateRequest();

    request.setFirstName(null);

    ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void createUserValidAgeReturnsConflict() throws Exception {
    UserCreateOrFullUpdateRequest request = getUserCreateOrFullUpdateRequest();

    request.setBirthday(LocalDate.now());

    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }

  @Test
  void createUserSameEmailReturnsConflict() throws Exception {
    UserCreateOrFullUpdateRequest request = getUserCreateOrFullUpdateRequest();

    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isCreated());

    mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(MockMvcResultMatchers.status().isConflict());
  }


  @SuppressWarnings("MagicNumber")
  private static UserCreateOrFullUpdateRequest getUserCreateOrFullUpdateRequest() {
    UserCreateOrFullUpdateRequest request = new UserCreateOrFullUpdateRequest();
    request.setEmail("example@example.com");
    request.setFirstName("Elvis");
    request.setLastName("Presley");
    request.setBirthday(LocalDate.of(2000, 1, 1));
    request.setAddress("123 Main St");
    request.setPhone("+380671234578");
    return request;
  }

}
