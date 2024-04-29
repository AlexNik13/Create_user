package create.user.user.dto.response;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import create.user.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {

  private Long id;
  private String email;
  private String firstName;
  private String lastName;
  private LocalDate birthday;
  private String address;
  private String phone;
  private ZonedDateTime createdAt;
  private ZonedDateTime modifiedAt;

  public static UserResponse of(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .birthday(user.getBirthday())
        .address(user.getAddress())
        .phone(user.getPhone())
        .createdAt(user.getCreatedAt())
        .modifiedAt(user.getModifiedAt())
        .build();
  }

}
