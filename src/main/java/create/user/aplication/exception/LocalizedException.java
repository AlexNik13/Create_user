package create.user.aplication.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;

public abstract class LocalizedException extends RuntimeException implements MessageSourceResolvable {

  private final String code;
  private final Object[] args;

  protected LocalizedException(String code, Object[] args, Throwable cause) {
    super(cause);
    this.code = code;
    this.args = args;
  }

  protected LocalizedException(String code) {
    this(code, null, null);
  }

  protected LocalizedException(String code, Throwable cause) {
    this(code, null, cause);
  }

  protected LocalizedException(String code, Object... args) {
    this(code, args, null);
  }

  public abstract HttpStatus getStatusCode();

  @Override
  public String[] getCodes() {
    return new String[]{code};
  }

  @Override
  public Object[] getArguments() {
    return args;
  }

  @Override
  public String getDefaultMessage() {
    return code;
  }

}
