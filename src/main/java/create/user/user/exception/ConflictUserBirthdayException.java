package create.user.user.exception;

import create.user.aplication.exception.ConflictException;

public class ConflictUserBirthdayException extends ConflictException {

  public ConflictUserBirthdayException(int years) {
    super("exception.conflict.user.leastYears", years);
  }

}
