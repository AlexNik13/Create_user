package create.user.user.service;

import create.user.aplication.utils.Pageable;
import create.user.user.dto.request.UserCreateRequest;
import create.user.user.model.User;

public interface UserService {

  User create(UserCreateRequest request);

  Pageable<User> findAll(long offset, long limit);

}
