package create.user.aplication.exception;

import org.springframework.http.HttpStatus;


public abstract class ConflictException extends LocalizedException {

  public ConflictException(String code, Object[] args, Throwable cause) {
    super(code, args, cause);
  }

  public ConflictException(String code) {
    super(code);
  }

  public ConflictException(String code, Throwable cause) {
    super(code, cause);
  }

  public ConflictException(String code, Object... args) {
    super(code, args);
  }

  @Override
  public HttpStatus getStatusCode() {
    return HttpStatus.CONFLICT;
  }

}
