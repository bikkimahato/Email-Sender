package com.bikki.emailsender.exceptions;

import com.bikki.emailsender.constants.EmailExceptionConstants;
import org.springframework.http.HttpStatus;

public class EmailGenericException extends EmailException {

  private final HttpStatus status;

  public EmailGenericException(
      String errMsg, EmailExceptionConstants.EMAIL_APP_ERROR_CODES errorCode, HttpStatus status) {
    super(errMsg, errorCode);
    this.status = status;
  }

  public EmailGenericException(String msg, HttpStatus status) {
    super(msg);
    this.status = status;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return this.status;
  }
}
