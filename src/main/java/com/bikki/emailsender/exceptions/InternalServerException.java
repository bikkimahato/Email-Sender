package com.bikki.emailsender.exceptions;

import com.bikki.emailsender.constants.EmailExceptionConstants;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

public class InternalServerException extends EmailException {

  private static final long serialVersionUID = 1L;

  public InternalServerException(String s) {
    super(s);
  }

  public InternalServerException(
      String errMsg, EmailExceptionConstants.EMAIL_APP_ERROR_CODES errorCode) {
    super(errMsg, errorCode);
  }

  @NotNull
  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
