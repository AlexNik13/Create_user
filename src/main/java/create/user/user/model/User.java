package create.user.user.model;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

  private Long id;
  private String email;
  private String firstName;
  private String lastName;
  private LocalDate birthday;
  private String address;
  private String phone;
  private ZonedDateTime createdAt;
  private ZonedDateTime modifiedAt;

}
