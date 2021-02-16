package com.bikki.emailsender.constants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Slf4j
public class EmailConstants implements Serializable {
  public static final String EMAIL_DATE_FORMAT_WITH_TIME = "";

  public enum RESPONSE_STATUS {
    success,
    failure
  }
}
