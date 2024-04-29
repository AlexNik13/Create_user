package create.user.user.reposytori;

import create.user.aplication.utils.Pageable;
import create.user.user.model.User;

public interface UserRepository {

  User save(User user);

  boolean existsByEmail(String email);

  Pageable<User> findAll(long offset, long limit);

}
