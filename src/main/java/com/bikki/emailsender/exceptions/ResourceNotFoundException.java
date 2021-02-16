package com.bikki.emailsender.exceptions;

import com.bikki.emailsender.constants.EmailExceptionConstants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

@ResponseStatus
public class ResourceNotFoundException extends EmailException {

  private static final long serialVersionUID = 1L;

  public ResourceNotFoundException(String msg) {
    super(msg);
  }

  public ResourceNotFoundException(
      String errMsg, EmailExceptionConstants.EMAIL_APP_ERROR_CODES errorCode) {
    super(errMsg, errorCode);
  }

  @NotNull
  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.NOT_FOUND;
  }
}
