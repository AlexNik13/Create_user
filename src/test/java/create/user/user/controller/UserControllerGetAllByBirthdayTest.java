package create.user.user.controller;

import java.time.LocalDate;

import com.fasterxml.jackson.core.type.TypeReference;
import create.user.aplication.utils.Pageable;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerGetAllByBirthdayTest extends UserMockMvcTestBase {

  @Autowired
  private UserRepositorySpring userRepository;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
  }

  @Test
  void getAllByBirthday() throws Exception {
    UserResponse userResponse = super.createUser(UserData.getUserCreateOrFullUpdateRequest());

    LocalDate birthday = userResponse.getBirthday();
    LocalDate from = birthday.minusYears(1);
    LocalDate to = birthday.plusYears(1);

    final long offset = 0;
    final long limit = 10;

    Pageable<UserResponse> response = mvcResultGetAllByBirthday(from, to, offset, limit);

    Assertions.assertEquals(1, response.getTotal());
    Assertions.assertEquals(offset, response.getOffset());
    Assertions.assertEquals(limit, response.getLimit());
  }


  @Test
  void getAllByBirthdayPageable() throws Exception {
    UserCreateOrFullUpdateRequest userResponse = UserData.getUserCreateOrFullUpdateRequest();
    String email = userResponse.getEmail();

    LocalDate birthday = userResponse.getBirthday();
    LocalDate from = birthday.minusYears(1);
    LocalDate to = birthday.plusYears(1);

    final long offset = 1;
    final long limit = 3;
    final long total = 6;

    for (int i = 0; i < total; i++) {
      String uniqueEmail = i + email;
      userResponse.setEmail(uniqueEmail);
      super.createUser(userResponse);
    }

    Pageable<UserResponse> response = mvcResultGetAllByBirthday(from, to, offset, limit);

    Assertions.assertEquals(total, response.getTotal());
    Assertions.assertEquals(offset, response.getOffset());
    Assertions.assertEquals(limit, response.getLimit());
    Assertions.assertEquals(limit, response.getValues().size());
  }


  private Pageable<UserResponse> mvcResultGetAllByBirthday(
      LocalDate from,
      LocalDate to,
      long offset,
      long limit
  ) throws Exception {
    MvcResult result = super.mockMvc.perform(MockMvcRequestBuilders.get("/users")
            .param("from", from.toString())
            .param("to", to.toString())
            .param("offset", String.valueOf(offset))
            .param("limit", String.valueOf(limit))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();

    TypeReference<Pageable<UserResponse>> typeReference = new TypeReference<>() {
    };

    return objectMapper.readValue(content, typeReference);
  }

}
