package create.user.user.reposytori;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import create.user.aplication.utils.Pageable;
import create.user.user.exception.ConflictUnequalEmailException;
import create.user.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class UserCustomRepositoryImplTest {

  private static final int OFFSET = 0;

  private final UserRepositorySpring userRepository = new UserCustomRepositoryImpl();

  @AfterEach
  void setUp() {
    userRepository.deleteAll();
  }

  @Test
  void createAndFindAll() {
    userRepository.create(createUser("1email@email.com"));
    userRepository.create(createUser("2email@email.com"));
    userRepository.create(createUser("3email@email.com"));
    final int totalSave = 3;

    final long limit = 2;

    Pageable<User> userPageable = userRepository.findAll(OFFSET, limit);

    Assertions.assertEquals(OFFSET, userPageable.getOffset());
    Assertions.assertEquals(limit, userPageable.getLimit());
    Assertions.assertEquals(totalSave, userPageable.getTotal());
    List<User> users = userPageable.getValues();
    Assertions.assertEquals(limit, users.size());
  }

  @Test
  void createUnequalEmail() {
    String email1 = "UnequalEmail@email.com";
    String email2 = "unequalEmail@email.com";

    User user = new User();
    user.setEmail(email1);
    userRepository.create(user);

    Executable saveWithSameEmail = () -> {
      User userWithSameEmail = new User();
      userWithSameEmail.setEmail(email2);
      userRepository.create(userWithSameEmail);
    };

    Assertions.assertThrows(ConflictUnequalEmailException.class, saveWithSameEmail);
  }

  @Test
  void findById() {
    User user = createUser("findById@email.com");

    User saveUser = userRepository.create(user);

    User userFindById = userRepository.findById(saveUser.getId()).orElse(null);

    Assertions.assertEquals(user.getEmail(), userFindById.getEmail());
    Assertions.assertEquals(user.getFirstName(), userFindById.getFirstName());
    Assertions.assertEquals(user.getLastName(), userFindById.getLastName());
    Assertions.assertEquals(user.getAddress(), userFindById.getAddress());
    Assertions.assertEquals(user.getPhone(), userFindById.getPhone());
    Assertions.assertEquals(user.getBirthday(), userFindById.getBirthday());
    Assertions.assertEquals(user.getCreatedAt(), userFindById.getCreatedAt());
    Assertions.assertEquals(user.getModifiedAt(), userFindById.getModifiedAt());
  }

  @Test
  void findByIdIsEmpty() {
    Optional<User> user = userRepository.findById(0L);
    Assertions.assertTrue(user.isEmpty());
  }

  @Test
  @SuppressWarnings("MagicNumber")
  void findByBirthdayBetweenFromAndTo() {
    User user1 = createUser("1findByBirthdayFromTo@email.com");
    User user2 = createUser("2findByBirthdayFromTo@email.com");
    User user3 = createUser("3findByBirthdayFromTo@email.com");
    User user4 = createUser("4findByBirthdayFromTo@email.com");

    user1.setBirthday(LocalDate.of(2000, 1, 1));
    userRepository.create(user1);

    user2.setBirthday(LocalDate.of(2005, 1, 1));
    userRepository.create(user2);

    user3.setBirthday(LocalDate.of(2010, 1, 1));
    userRepository.create(user3);

    user4.setBirthday(LocalDate.of(2015, 1, 1));
    userRepository.create(user4);

    LocalDate from = LocalDate.of(2005, 1, 1);
    LocalDate to = LocalDate.of(2014, 1, 1);

    Pageable<User> userBirthday = userRepository.findByBirthdayBetweenFromAndTo(from, to, 0, 10);

    Assertions.assertEquals(2, userBirthday.getValues().size());
  }

  @Test
  void deleteById() {
    User user = createUser("deleteById@email.com");

    User saveUser = userRepository.create(user);

    Long id = saveUser.getId();

    userRepository.deleteById(id);

    Optional<User> findUser = userRepository.findById(id);
    Assertions.assertFalse(findUser.isPresent());
  }

  @Test
  @SuppressWarnings("MagicNumber")
  void deleteAll() {
    userRepository.create(createUser("deleteAll@email.com"));
    userRepository.deleteAll();
    final long limit = 10;

    Pageable<User> userPageable = userRepository.findAll(OFFSET, limit);
    Assertions.assertTrue(userPageable.getValues().isEmpty());
    Assertions.assertEquals(0, userPageable.getTotal());
  }

  private static User createUser(String email) {
    final String firstName = "First Name";
    final String lastName = "Last Name";
    final String address = "Street 1 house 3";
    final String phoneNumber = "123456789";

    final int year = 2000;
    final int month = 1;
    final int day = 1;
    LocalDate birthday = LocalDate.of(year, month, day);

    ZonedDateTime dateTime = ZonedDateTime.now();

    User user = new User();
    user.setEmail(email);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setBirthday(birthday);
    user.setAddress(address);
    user.setPhone(phoneNumber);
    user.setCreatedAt(dateTime);
    user.setModifiedAt(dateTime);
    return user;
  }

}
