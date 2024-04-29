package create.user.user.dto.request;

import java.time.LocalDate;

import create.user.user.constraint.Phone;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPartialUpdateRequest {

  @Email
  private String email;

  private String firstName;

  private String lastName;

  private LocalDate birthday;

  private String address;

  @Phone
  private String phone;

}
