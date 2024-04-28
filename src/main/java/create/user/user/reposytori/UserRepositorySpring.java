package create.user.user.reposytori;

import java.time.LocalDate;
import java.util.Optional;

import create.user.aplication.utils.Pageable;
import create.user.user.model.User;

public interface UserRepositorySpring {

  User save(User user);

  Optional<User> findById(Long id);

  Pageable<User> findByBirthdayFromTo(LocalDate from, LocalDate to, long offset, long limit);

  Pageable<User> findAll(long offset, long limit);

  boolean existsByEmail(String email);

  void deleteById(Long id);

  void deleteAll();

}
