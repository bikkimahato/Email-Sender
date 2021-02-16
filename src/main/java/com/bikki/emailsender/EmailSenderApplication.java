package com.bikki.emailsender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@SpringBootApplication
public class EmailSenderApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmailSenderApplication.class, args);
  }

  @Bean
  public LocaleResolver localeResolver() {
    return new CookieLocaleResolver();
  }
}
