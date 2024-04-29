package create.user.user.reposytori;

import java.time.LocalDate;
import java.util.Optional;

import create.user.aplication.utils.Pageable;
import create.user.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private final UserRepositorySpring delegate;

  @Autowired
  public UserRepositoryImpl(
      UserRepositorySpring delegate
  ) {
    this.delegate = delegate;
  }

  @Override
  public User save(User user) {
    return delegate.create(user);
  }

  @Override
  public Optional<User> findById(long id) {
    return delegate.findById(id);
  }

  @Override
  public Pageable<User> findByBirthdayBetweenFromAndTo(
      LocalDate from,
      LocalDate to,
      long offset,
      long limit
  ) {
    return delegate.findByBirthdayBetweenFromAndTo(from, to, offset, limit);
  }

  @Override
  public boolean existsByEmail(String email) {
    return delegate.existsByEmail(email);
  }

  @Override
  public User update(User user) {
    return delegate.update(user);
  }

  @Override
  public void deleteById(Long id) {
    delegate.deleteById(id);
  }

}
