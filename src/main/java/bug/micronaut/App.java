package bug.micronaut;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.runtime.Micronaut;

import static io.micronaut.http.HttpStatus.BAD_REQUEST;

public class App {

  @Controller("/example/bug")
  public static final class Bug {

    @Get
    public HttpResponse<?> get() {
      return HttpResponse.status(BAD_REQUEST).body(new PgContent<>("error", false));
    }
  }

  public static final class PgContent<T> {
    @JsonProperty("word")
    public final String word;

    @JsonProperty("data")
    public final T data;

    @JsonCreator
    public PgContent(@JsonProperty("word") String word, @JsonProperty("data") T data) {
      this.word = word;
      this.data = data;
    }
  }

  public static void main(String[] args) {
    Micronaut.run(App.class, args);
  }
}
