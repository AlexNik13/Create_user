package create.user.user.exception;

import create.user.aplication.exception.ConflictException;

public class ConflictUserEmailExistsException extends ConflictException {

  public ConflictUserEmailExistsException(String email) {
    super("exception.conflict.user.emailExists", email);
  }

}
