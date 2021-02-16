package com.bikki.emailsender.exceptions;

import com.bikki.emailsender.constants.EmailExceptionConstants;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

public class DuplicateEntryException extends EmailException {

  private static final long serialVersionUID = 1L;

  public DuplicateEntryException(String msg) {
    super(msg);
  }

  public DuplicateEntryException(
      String errMsg, EmailExceptionConstants.EMAIL_APP_ERROR_CODES errorCode) {
    super(errMsg, errorCode);
  }

  @NotNull
  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.EXPECTATION_FAILED;
  }
}
