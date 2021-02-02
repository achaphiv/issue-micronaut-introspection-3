package com.example;

import java.beans.ConstructorProperties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.micronaut.core.annotation.Introspected;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import io.micronaut.jackson.modules.BeanIntrospectionModule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntrospectedAndJsonNamingTest {
  @Introspected
  @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
  public static final class Example {
    final String fooBar;

    @ConstructorProperties({ "fooBar" })
    public Example(String fooBar) {
      this.fooBar = fooBar;
    }
  }

  @Test
  public void deserializeViaReflection() throws Exception {
    ObjectMapper mapper = new ObjectMapper();

    Example actual = mapper.readValue("{ \"FooBar\": \"good\" }", Example.class);

    Assertions.assertEquals("good", actual.fooBar);
  }

  @Test
  public void deserializeViaBeanIntrospection() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new BeanIntrospectionModule());

    Example actual = mapper.readValue("{ \"FooBar\": \"bad\" }", Example.class);

    Assertions.assertEquals("bad", actual.fooBar);
  }
}
