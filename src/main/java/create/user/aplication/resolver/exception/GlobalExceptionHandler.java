package create.user.aplication.resolver.exception;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import create.user.aplication.exception.LocalizedException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private final MessageSource messageSource;

  public GlobalExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      MethodArgumentNotValidException ex,
      WebRequest request
  ) {
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> "Field '" + error.getField() + "': " + error.getDefaultMessage())
        .collect(Collectors.toList());

    String errorMessage =
        "Validation error: " + StringUtils.collectionToCommaDelimitedString(errors);

    log.error(errorMessage, ex);

    ProblemDetail exBody = ex.getBody();

    ErrorResponse errorResponse = new ErrorResponse(
        LocalDateTime.now(),
        exBody.getStatus(),
        exBody.getDetail(),
        errorMessage,
        request.getDescription(false)
    );

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(LocalizedException.class)
  public ResponseEntity<Object> handleLocalizedException(
      LocalizedException ex,
      WebRequest request
  ) {

    String errorMessage = resolveLocalizedErrorMessage(ex);

    log.error(errorMessage, ex);

    ErrorResponse errorResponse = new ErrorResponse(
        LocalDateTime.now(),
        ex.getStatusCode().value(),
        ex.getStatusCode().getReasonPhrase(),
        errorMessage,
        request.getDescription(false)
    );

    return new ResponseEntity<>(errorResponse, ex.getStatusCode());
  }

  private String resolveLocalizedErrorMessage(LocalizedException ex) {
    String code = ex.getCode();
    Object[] args = ex.getArguments();
    return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
  }

  @Getter
  public class ErrorResponse {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message,
        String path) {
      this.timestamp = timestamp;
      this.status = status;
      this.error = error;
      this.message = message;
      this.path = path;
    }

  }

}
