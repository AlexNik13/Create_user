package create.user.user.reposytori;

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
    return delegate.save(user);
  }

  @Override
  public boolean existsByEmail(String email) {
    return delegate.existsByEmail(email);
  }

  @Override
  public Pageable<User> findAll(long offset, long limit) {
    return delegate.findAll(offset, limit);
  }

}
