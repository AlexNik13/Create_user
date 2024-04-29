package create.user.user.reposytori;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import create.user.aplication.utils.Pageable;
import create.user.user.exception.ConflictUnequalEmailException;
import create.user.user.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserCustomRepositoryImpl implements UserRepositorySpring {

  private static final Map<Long, User> userMap = new HashMap<>();
  private static final AtomicLong counter = new AtomicLong(0);

  @Override
  public User create(User user) {
    Long id;

    if (existsByEmail(user.getEmail())) {
      throw new ConflictUnequalEmailException(user.getEmail());
    }

    id = counter.incrementAndGet();
    user.setId(id);

    userMap.put(id, user);
    return userMap.get(id);
  }

  @Override
  public Optional<User> findById(Long id) {
    return Optional.ofNullable(userMap.get(id));
  }

  @Override
  public Pageable<User> findByBirthdayBetweenFromAndTo(
      LocalDate from,
      LocalDate to,
      long offset,
      long limit
  ) {
    List<User> userList = userMap.values()
        .stream()
        .filter(user -> {
          LocalDate birthday = user.getBirthday();
          return (
              birthday.isBefore(from) || birthday.isEqual(from))
              && (birthday.isBefore(to) || birthday.isEqual(to));
        })
        .toList();

    Pageable<User> userPageable = new Pageable<>();

    userPageable.setOffset(offset);
    userPageable.setLimit(limit);
    userPageable.setTotal(userList.size());

    userPageable.setValues(
        userList.stream()
            .skip(offset)
            .limit(limit)
            .toList()
    );

    return userPageable;
  }

  @Override
  public Pageable<User> findAll(long offset, long limit) {
    List<User> userList = userMap.values()
        .stream()
        .skip(offset)
        .limit(limit)
        .toList();

    Pageable<User> userPageable = new Pageable<>();

    userPageable.setLimit(limit);
    userPageable.setOffset(offset);
    userPageable.setTotal(userMap.size());

    userPageable.setValues(userList);

    return userPageable;
  }

  @Override
  public boolean existsByEmail(String email) {
    String emailLowerCase = email.toLowerCase();
    return userMap.values()
        .stream()
        .anyMatch(u -> u.getEmail().toLowerCase().equals(emailLowerCase));
  }

  @Override
  public User update(User user) {
    checkUnequalEmailByUser(user);

    userMap.put(user.getId(), user);
    return user;
  }

  private void checkUnequalEmailByUser(User user) {
    String email = user.getEmail();
    Long id = user.getId();

    for (User existingUser : userMap.values()) {
      if (!existingUser.getId().equals(id) && existingUser.getEmail().equals(email)) {
        throw new ConflictUnequalEmailException(user.getEmail());
      }
    }
  }

  @Override
  public void deleteById(Long id) {
    userMap.remove(id);
  }

  @Override
  public void deleteAll() {
    userMap.clear();
  }

}
