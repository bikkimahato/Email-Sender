package com.bikki.emailsender.exceptions;

import com.bikki.emailsender.constants.EmailExceptionConstants;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

public class BadRequestException extends EmailException {

  private static final long serialVersionUID = 1L;

  public BadRequestException(String errMsg) {
    super(errMsg);
  }

  public BadRequestException(
      String errMsg, EmailExceptionConstants.EMAIL_APP_ERROR_CODES errorCode) {
    super(errMsg, errorCode);
  }

  @NotNull
  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.BAD_REQUEST;
  }
}
