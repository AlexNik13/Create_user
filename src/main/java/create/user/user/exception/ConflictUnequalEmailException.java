package create.user.user.exception;

import create.user.aplication.exception.ConflictException;

public class ConflictUnequalEmailException extends ConflictException {

  public ConflictUnequalEmailException(String email) {
    super("exception.conflict.user.unequalEmail");
  }

}
