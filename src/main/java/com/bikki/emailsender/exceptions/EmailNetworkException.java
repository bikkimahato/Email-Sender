package com.bikki.emailsender.exceptions;

import com.bikki.emailsender.constants.EmailExceptionConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmailNetworkException extends RuntimeException {

  private String message = EmailExceptionConstants.SOMETHING_WENT_WRONG;
  private ErrorDetails errorDetails;
  private HttpStatus status = HttpStatus.BAD_REQUEST;

  public EmailNetworkException(String message, ErrorDetails errorDetails, HttpStatus status) {
    super(message);
    this.message = message;
    this.errorDetails = errorDetails;
    this.status = status;
  }

  public EmailNetworkException(@NotNull ErrorDetails errorDetails) {
    super(errorDetails.getMessage());
    this.errorDetails = errorDetails;
    this.message = errorDetails.getMessage();
    this.status = HttpStatus.valueOf(errorDetails.getResponseCode());
  }
}
