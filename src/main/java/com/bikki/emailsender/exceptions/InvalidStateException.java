package com.bikki.emailsender.exceptions;

import com.bikki.emailsender.constants.EmailExceptionConstants;
import org.springframework.http.HttpStatus;

public class InvalidStateException extends EmailException {

  private static final long serialVersionUID = 1L;

  public InvalidStateException(String msg) {
    super(msg);
  }

  public InvalidStateException(
      String errMsg, EmailExceptionConstants.EMAIL_APP_ERROR_CODES errorCode) {
    super(errMsg, errorCode);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.NOT_ACCEPTABLE;
  }
}
