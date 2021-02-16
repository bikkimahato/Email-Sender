package com.bikki.emailsender.exceptions;

import com.bikki.emailsender.utils.EmailUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDetails implements Serializable {

  private String timestamp;

  @JsonProperty("displayMessage")
  private String message;

  private Integer responseCode;

  @JsonProperty("details")
  private ErrorDeveloperDetails details;

  public ErrorDetails(Date timestamp, String message, HttpStatus httpStatus, String appStatusCode) {
    this.timestamp = EmailUtils.getDateWithTimeAsString(timestamp);
    this.message = message;
    this.responseCode = httpStatus.value();
    this.details = new ErrorDeveloperDetails(httpStatus.name(), appStatusCode, message);
  }

  public ErrorDetails(Date timestamp, String message) {
    this.timestamp = EmailUtils.getDateWithTimeAsString(timestamp);
    this.message = message;
  }

  public ErrorDetails(Date date, String message, String description) {
    super();
    this.timestamp = EmailUtils.getDateWithTimeAsString(date);
    this.message = message;
    this.details = new ErrorDeveloperDetails(description);
  }
}
