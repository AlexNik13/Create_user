package create.user.user.dto.request;

import java.time.LocalDate;

import create.user.user.constraint.Phone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {

  @Email
  @NotBlank
  private String email;

  @NotNull
  private String firstName;

  @NotBlank
  private String lastName;

  @NotNull
  private LocalDate birthday;

  private String address;

  @Phone
  private String phone;

}
