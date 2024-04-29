package create.user.user.exception;

import create.user.aplication.exception.NotFoundException;

public class NotFoundUserByIdException extends NotFoundException {

  public NotFoundUserByIdException(Long id) {
    super("exception.notFound.user.byId", id);
  }
}
