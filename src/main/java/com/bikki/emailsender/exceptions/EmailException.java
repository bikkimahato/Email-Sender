package com.bikki.emailsender.exceptions;

import com.bikki.emailsender.constants.EmailExceptionConstants;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class EmailException extends RuntimeException {

  private EmailExceptionConstants.EMAIL_APP_ERROR_CODES errorCode;

  public EmailException(String msg) {
    super(msg);
  }

  public EmailException(String msg, EmailExceptionConstants.EMAIL_APP_ERROR_CODES errorCode) {
    super(msg);
    this.errorCode = errorCode;
  }

  public EmailException(
      String msg, EmailExceptionConstants.EMAIL_APP_ERROR_CODES errorCode, Throwable cause) {
    super(msg, cause);
    this.errorCode = errorCode;
  }

  public EmailException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public EmailException(Throwable cause) {
    super(cause);
  }

  public abstract HttpStatus getHttpStatus();
}
