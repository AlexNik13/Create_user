package create.user.aplication.utils;

import java.util.Objects;
import java.util.function.Consumer;

public final class NullableUtils {

  private NullableUtils() {
  }

  public static <T> void applyIfNotNull(Consumer<T> setter, T value) {
    Objects.requireNonNull(setter, "Setter must not be null");

    if (value != null) {
      setter.accept(value);
    }
  }

}
