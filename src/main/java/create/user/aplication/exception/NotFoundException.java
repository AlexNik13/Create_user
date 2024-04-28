package create.user.aplication.exception;

import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends LocalizedException {

  public NotFoundException(String code, Object[] args, Throwable cause) {
    super(code, args, cause);
  }

  public NotFoundException(String code) {
    super(code);
  }

  public NotFoundException(String code, Throwable cause) {
    super(code, cause);
  }

  public NotFoundException(String code, Object... args) {
    super(code, args);
  }

  @Override
  public HttpStatus getStatusCode() {
    return HttpStatus.NOT_FOUND;
  }

}
