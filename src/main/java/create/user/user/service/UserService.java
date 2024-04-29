package create.user.user.service;

import java.time.LocalDate;

import create.user.aplication.utils.Pageable;
import create.user.user.dto.request.UserCreateOrFullUpdateRequest;
import create.user.user.dto.request.UserPartialUpdateRequest;
import create.user.user.model.User;

public interface UserService {

  User create(UserCreateOrFullUpdateRequest request);

  User findById(Long id);

  Pageable<User> findByBirthdayBetweenFromAndTo(
      LocalDate from,
      LocalDate to,
      long offset,
      long limit
  );

  User fullUpdate(Long id, UserCreateOrFullUpdateRequest request);

  User partialUpdate(Long id, UserPartialUpdateRequest request);

  void deleteById(Long id);

}
