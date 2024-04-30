package create.user.user.controller.data;

import java.time.LocalDate;

import create.user.user.dto.request.UserCreateOrFullUpdateRequest;

public final class UserData {

  private UserData() {
  }

  @SuppressWarnings("MagicNumber")
  public static UserCreateOrFullUpdateRequest getUserCreateOrFullUpdateRequest() {
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
