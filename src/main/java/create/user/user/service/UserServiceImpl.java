package create.user.user.service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import create.user.aplication.utils.NullableUtils;
import create.user.aplication.utils.Pageable;
import create.user.user.dto.request.UserCreateOrFullUpdateRequest;
import create.user.user.dto.request.UserPartialUpdateRequest;
import create.user.user.exception.ConflictUserBirthdayException;
import create.user.user.exception.ConflictUserEmailExistsException;
import create.user.user.exception.NotFoundUserByIdException;
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
  public User create(UserCreateOrFullUpdateRequest request) {
    String email = request.getEmail();
    log.info("Creating user by email: {}", email);

    checkEmailExists(email);

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

  @Override
  public User findById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(
            () -> new NotFoundUserByIdException(id)
        );
  }

  private void checkEmailExists(String email) {
    if (userRepository.existsByEmail(email)) {
      log.error("User with email {} already exists", email);
      throw new ConflictUserEmailExistsException(email);
    }
  }

  @Override
  public Pageable<User> findByBirthdayBetweenFromAndTo(
      LocalDate from,
      LocalDate to,
      long offset,
      long limit
  ) {
    return userRepository.findByBirthdayBetweenFromAndTo(from, to, offset, limit);
  }

  @Override
  public User fullUpdate(Long id, UserCreateOrFullUpdateRequest request) {
    log.info("User updating all fields by id: {}", id);

    LocalDate birthday = request.getBirthday();
    checkBirthday(birthday);

    User user = findById(id);

    user.setEmail(request.getEmail());
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setBirthday(birthday);
    user.setAddress(request.getAddress());
    user.setPhone(request.getPhone());
    user.setModifiedAt(ZonedDateTime.now(clock));
    User saveUser = userRepository.update(user);
    log.info("User has be updated all fields by id: {}; ", id);
    return saveUser;
  }

  @Override
  public User partialUpdate(Long id, UserPartialUpdateRequest request) {
    log.info("User updating partial fields by id: {}", id);

    LocalDate birthday = request.getBirthday();
    NullableUtils.applyIfNotNull(this::checkBirthday, birthday);

    User user = findById(id);

    NullableUtils.applyIfNotNull(user::setEmail, request.getEmail());
    NullableUtils.applyIfNotNull(user::setFirstName, request.getFirstName());
    NullableUtils.applyIfNotNull(user::setLastName, request.getLastName());
    NullableUtils.applyIfNotNull(user::setBirthday, birthday);
    NullableUtils.applyIfNotNull(user::setAddress, request.getAddress());
    NullableUtils.applyIfNotNull(user::setPhone, request.getPhone());
    user.setModifiedAt(ZonedDateTime.now(clock));

    User saveUser = userRepository.update(user);

    log.info("User has be updated partial fields by id: {}; ", id);
    return saveUser;
  }

  @Override
  public void deleteById(Long id) {
    userRepository.deleteById(id);
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
