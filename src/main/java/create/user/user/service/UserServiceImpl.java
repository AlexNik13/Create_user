package create.user.user.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import create.user.aplication.utils.Pageable;
import create.user.user.dto.request.UserCreateRequest;
import create.user.user.exception.ConflictUnequalEmailException;
import create.user.user.exception.ConflictUserBirthdayException;
import create.user.user.model.User;
import create.user.user.reposytori.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Value("${application.user.registration.min.age}")
  private int minAge;

  private final UserRepository userRepository;
  private final Clock clock;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository,
      Clock clock
  ) {
    this.userRepository = userRepository;
    this.clock = clock;
  }

  @Override
  public User create(UserCreateRequest request) {
    String email = request.getEmail();
    log.info("Creating user by email: {}", email);

    existsByEmail(email);

    LocalDate birthday = request.getBirthday();
    checkBirthday(birthday);

    ZonedDateTime nowDateTime = ZonedDateTime.now(clock);

    User user = new User();

    user.setEmail(email);
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setBirthday(birthday);
    user.setAddress(request.getAddress());
    user.setPhone(request.getPhone());
    user.setCreatedAt(nowDateTime);
    user.setModifiedAt(nowDateTime);

    User saveUser = userRepository.save(user);

    log.info("User has be created by email: {}; userIs: {} ", email, saveUser.getId());

    return user;
  }

  private void existsByEmail(String email) {
    if (userRepository.existsByEmail(email)) {
      log.error("User with email {} already exists", email);
      throw new ConflictUnequalEmailException(email);
    }
  }

  @Override
  public Pageable<User> findAll(long offset, long limit) {
    return userRepository.findAll(offset, limit);
  }

  private void checkBirthday(LocalDate birthday) {
    LocalDate minimumAllowedBirthday = LocalDate.now(clock)
        .minusYears(minAge);

    if (birthday.isAfter(minimumAllowedBirthday)) {
      log.error("User must be at least {} years old", minAge);
      throw new ConflictUserBirthdayException(minAge);
    }
  }

}
