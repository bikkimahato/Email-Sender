package com.bikki.emailsender.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MailUtil {

  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.password}")
  private String password;

  @Value("${spring.mail.properties.mail.smtp.starttls.required}")
  private String starttls_required;

  @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
  private String connection_timeout;

  @Value("${spring.mail.properties.mail.smtp.timeout}")
  private String timeout;

  @Value("${spring.mail.properties.mail.smtp.writetimeout}")
  private String write_timeout;

  @Bean
  public JavaMailSender javaMailService() {

    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost("smtp.gmail.com");
    javaMailSender.setPort(587);

    javaMailSender.setUsername(username);
    javaMailSender.setPassword(password);

    Properties props = javaMailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("spring.mail.properties.mail.smtp.starttls.required", starttls_required);
    props.put("spring.mail.properties.mail.smtp.connectiontimeout", connection_timeout);
    props.put("spring.mail.properties.mail.smtp.timeout", timeout);
    props.put("spring.mail.properties.mail.smtp.writetimeout", write_timeout);
    javaMailSender.setJavaMailProperties(props);

    return javaMailSender;
  }
}
