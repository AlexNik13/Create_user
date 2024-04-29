package create.user.user.controller;

import java.time.LocalDate;

import create.user.aplication.utils.Pageable;
import create.user.user.dto.request.UserCreateOrFullUpdateRequest;
import create.user.user.dto.request.UserPartialUpdateRequest;
import create.user.user.dto.response.UserResponse;
import create.user.user.model.User;
import create.user.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(
      UserService userService
  ) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<Pageable<UserResponse>> getAllByBirthday(
      @RequestParam LocalDate from,
      @RequestParam LocalDate to,
      @RequestParam(defaultValue = "0") long offset,
      @RequestParam(defaultValue = "10") long limit
  ) {
    Pageable<User> users = userService.findByBirthdayBetweenFromAndTo(from, to, offset, limit);

    Pageable<UserResponse> response = users.map(UserResponse::of);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<UserResponse> createUser(
      @RequestBody @Validated UserCreateOrFullUpdateRequest request) {
    User user = userService.create(request);

    UserResponse response = UserResponse.of(user);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
    User user = userService.findById(id);

    UserResponse response = UserResponse.of(user);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> fullUpdateUser(
      @PathVariable Long id,
      @RequestBody @Validated UserCreateOrFullUpdateRequest request
  ) {
    User user = userService.fullUpdate(id, request);
    UserResponse response = UserResponse.of(user);
    return ResponseEntity.ok(response);
  }

  @PatchMapping
  public ResponseEntity<UserResponse> partialUpdateUser(
      @PathVariable Long id,
      @RequestBody @Validated UserPartialUpdateRequest request
  ) {
    User user = userService.partialUpdate(id, request);
    UserResponse response = UserResponse.of(user);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
    userService.deleteById(id);
    return ResponseEntity.ok()
        .build();
  }

}
