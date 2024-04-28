package create.user.aplication.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pageable<T> {

  private long offset;
  private long limit;
  private long total;

  private List<T> values = new ArrayList<T>();

}
