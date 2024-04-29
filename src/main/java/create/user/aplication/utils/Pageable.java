package create.user.aplication.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pageable<T> {

  private long offset;
  private long limit;
  private long total;

  private List<T> values = new ArrayList<T>();

  public <U> Pageable<U> map(Function<? super T, ? extends U> converter) {
    List<U> mappedValues = values.stream()
        .map(converter)
        .collect(Collectors.toList());

    Pageable<U> mappedPageable = new Pageable<>();
    mappedPageable.setOffset(offset);
    mappedPageable.setLimit(limit);
    mappedPageable.setTotal(total);
    mappedPageable.setValues(mappedValues);

    return mappedPageable;
  }

}
