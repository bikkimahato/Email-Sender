package com.bikki.emailsender.constants;

import lombok.Getter;
import lombok.ToString;

public class EmailExceptionConstants {
  public static final String SOMETHING_WENT_WRONG = "Something went wrong, Please try again!!";
  public static final String REQ_BODY_MISSING = "Required request body is missing.";
  public static final String INVALID_EMAIL = "Email(s) invalid";

  /** Application error codes go here */
  @ToString
  @Getter
  public enum EMAIL_APP_ERROR_CODES {
    INVALID_REQ("100001"),
    INVALID_REQ_HEADERS("100002"),
    INVALID_REQ_BODY("100003"),
    INVALID_REQ_PARAMS("100004"),
    INVALID_UPDATE_REQ("100005"),
    INVALID_EMAIL("100006"),
    INVALID_TOKEN("100007"),
    INVALID_CLIENT("100008"),
    BAD_REQUEST("200001"),
    INTERNAL_SERVER_ERROR("200002"),
    DOWNSTREAM_FAILURE("210001"),
    RESOURCE_ALREADY_EXIST("220001"),
    RESOURCE_NOT_FOUND("220002"),
    RESOURCE_NOT_ACTIVE("220003"),
    RESOURCE_INACTIVE("220004"),
    AUTHENTICATION_FAILURE("300001"),
    AUTHORIZATION_FAILURE("400001"),
    USER_NOT_VALID("110001");

    private final String code;

    EMAIL_APP_ERROR_CODES(String code) {
      this.code = code;
    }
  }
}
