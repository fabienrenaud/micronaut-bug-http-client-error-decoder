package bug.micronaut;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

@MicronautTest
class AppTest {

  @Inject EmbeddedServer server;

  @Inject
  @Client("/")
  HttpClient client;

  @Test
  void withErrorType() {
    Argument<App.PgContent> type = Argument.of(App.PgContent.class, Boolean.class);

    try {
      client.toBlocking().exchange(HttpRequest.GET("/example/bug"), type, type);
      fail();
    } catch (HttpClientResponseException ex) {
      HttpResponse<?> res = ex.getResponse();

      Optional<App.PgContent> optBody1 = res.getBody(type);
      Optional<App.PgContent> optBody2 = res.getBody(App.PgContent.class);
      System.out.println("optBody1: " + optBody1.isPresent());
      System.out.println("optBody2: " + optBody2.isPresent());
    }
  }

  @Test
  void withoutErrorType() {
    Argument<App.PgContent> type = Argument.of(App.PgContent.class, Boolean.class);

    try {
      client.toBlocking().exchange(HttpRequest.GET("/example/bug"), type);
      fail();
    } catch (HttpClientResponseException ex) {
      HttpResponse<?> res = ex.getResponse();

      Optional<App.PgContent> optBody1 = res.getBody(type);
      Optional<App.PgContent> optBody2 = res.getBody(App.PgContent.class);
      System.out.println("optBody1: " + optBody1.isPresent());
      System.out.println("optBody2: " + optBody2.isPresent());
    }
  }
}
