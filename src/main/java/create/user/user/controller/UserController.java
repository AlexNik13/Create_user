package create.user.user.controller;

import create.user.aplication.utils.Pageable;
import create.user.user.dto.request.UserCreateRequest;
import create.user.user.dto.response.UserResponse;
import create.user.user.model.User;
import create.user.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ResponseEntity<Pageable<UserResponse>> getAllUsers(
      @RequestParam(defaultValue = "0") long offset,
      @RequestParam(defaultValue = "10") long limit
  ) {
    Pageable<User> users = userService.findAll(offset, limit);

    Pageable<UserResponse> response = users.map(UserResponse::of);

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<UserResponse> createUser(
      @RequestBody @Validated UserCreateRequest request) {
    User user = userService.create(request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(UserResponse.of(user));
  }

}
