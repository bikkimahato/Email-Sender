package com.bikki.emailsender.exceptions;

import com.bikki.emailsender.constants.EmailExceptionConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

@ResponseStatus
public class ForbiddenException extends EmailException {

  private static final long serialVersionUID = 1L;

  public ForbiddenException(String msg) {
    super(msg);
  }

  public ForbiddenException(
      String errMsg, EmailExceptionConstants.EMAIL_APP_ERROR_CODES errorCode) {
    super(errMsg, errorCode);
  }

  @NotNull
  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.FORBIDDEN;
  }
}
