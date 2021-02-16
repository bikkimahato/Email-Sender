package com.bikki.emailsender.exceptions;

import com.bikki.emailsender.constants.EmailConstants;
import com.bikki.emailsender.constants.EmailExceptionConstants;
import com.bikki.emailsender.pojos.BaseResponse;
import com.bikki.emailsender.utils.EmailUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.bikki.emailsender.constants.EmailExceptionConstants.EMAIL_APP_ERROR_CODES.*;
import static com.bikki.emailsender.constants.EmailExceptionConstants.REQ_BODY_MISSING;
import static com.google.common.base.Throwables.getRootCause;
import static com.google.common.base.Throwables.getStackTraceAsString;

@ControllerAdvice
@RestController
public class EmailExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<BaseResponse<ErrorDetails>> handleGenericException(
      Exception exception, WebRequest request) {

    ErrorDetails errorDetails =
        getErrorDetails(
            getRootCause(exception).getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            INTERNAL_SERVER_ERROR,
            getStackTraceAsString(exception));
    return new ResponseEntity<>(
        new BaseResponse<>(EmailConstants.RESPONSE_STATUS.failure.name(), errorDetails),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @NotNull
  @ExceptionHandler(EmailException.class)
  public final ResponseEntity<BaseResponse<ErrorDetails>> handleEmailException(
      EmailException emailException, WebRequest request) {

    EmailExceptionConstants.EMAIL_APP_ERROR_CODES errorCode =
        getEmailExceptionErrorCode(emailException);
    ErrorDetails errorDetails =
        getErrorDetails(
            emailException.getMessage(),
            emailException.getHttpStatus(),
            errorCode,
            emailException.getMessage());
    return new ResponseEntity<>(
        new BaseResponse<>(EmailConstants.RESPONSE_STATUS.failure.name(), errorDetails),
        emailException.getHttpStatus());
  }

  @NotNull
  @ExceptionHandler(EmailNetworkException.class)
  public final ResponseEntity<BaseResponse<ErrorDetails>> handleEmailNetworkException(
      EmailNetworkException emailException, WebRequest request) {
    ErrorDetails errorDetails = emailException.getErrorDetails();
    return new ResponseEntity<>(
        new BaseResponse<>(EmailConstants.RESPONSE_STATUS.failure.name(), errorDetails),
        emailException.getStatus());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public final ResponseEntity<BaseResponse<ErrorDetails>> handleIllegalArgumentException(
      IllegalArgumentException exception, WebRequest request) {

    ErrorDetails errorDetails =
        getErrorDetails(
            exception.getMessage(), HttpStatus.BAD_REQUEST, BAD_REQUEST, exception.getMessage());
    return new ResponseEntity<>(
        new BaseResponse<>(EmailConstants.RESPONSE_STATUS.failure.name(), errorDetails),
        HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    StringBuilder sb = new StringBuilder();
    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
      sb.append(error.getDefaultMessage());
      sb.append(";");
    }

    ErrorDetails error =
        getErrorDetails(
            sb.toString(),
            status,
            EmailExceptionConstants.EMAIL_APP_ERROR_CODES.INVALID_REQ_BODY,
            ex.getClass().getCanonicalName());
    return new ResponseEntity<>(
        new BaseResponse<>(EmailConstants.RESPONSE_STATUS.failure.name(), error),
        HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    String errorMessage;
    if (!StringUtils.isEmpty(ex.getMessage()) && ex.getMessage().contains(REQ_BODY_MISSING)) {
      errorMessage = REQ_BODY_MISSING;
    } else {
      errorMessage = getRootCause(ex).getMessage();
    }

    ErrorDetails error =
        getErrorDetails(errorMessage, status, INVALID_REQ_BODY, ex.getClass().getCanonicalName());
    return new ResponseEntity<>(
        new BaseResponse<>(EmailConstants.RESPONSE_STATUS.failure.name(), error), status);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      MissingServletRequestPartException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    ErrorDetails error =
        getErrorDetails(
            ex.getMessage(), status, INVALID_REQ_PARAMS, ex.getClass().getCanonicalName());
    return new ResponseEntity<>(
        new BaseResponse<>(EmailConstants.RESPONSE_STATUS.failure.name(), error), status);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    ErrorDetails error =
        getErrorDetails(
            ex.getMessage(),
            status,
            EmailExceptionConstants.EMAIL_APP_ERROR_CODES.INVALID_REQ_PARAMS,
            ex.getClass().getCanonicalName());
    return new ResponseEntity<>(
        new BaseResponse<>(EmailConstants.RESPONSE_STATUS.failure.name(), error), status);
  }

  private EmailExceptionConstants.EMAIL_APP_ERROR_CODES getEmailExceptionErrorCode(
      EmailException exception) {
    if (exception.getErrorCode() != null) return exception.getErrorCode();
    switch (exception.getHttpStatus()) {
      case BAD_REQUEST:
        return BAD_REQUEST;
      case UNAUTHORIZED:
        return AUTHORIZATION_FAILURE;
      case FORBIDDEN:
        return AUTHENTICATION_FAILURE;
      case INTERNAL_SERVER_ERROR:
      default:
        return INTERNAL_SERVER_ERROR;
    }
  }

  private ErrorDetails getErrorDetails(
      String displayMsg,
      HttpStatus status,
      EmailExceptionConstants.EMAIL_APP_ERROR_CODES errorCodes,
      String appErrorMsg) {
    ErrorDeveloperDetails errorDeveloperDetails =
        ErrorDeveloperDetails.builder()
            .applicationErrorCode(errorCodes.getCode())
            .applicationErrorMessage(appErrorMsg)
            .applicationError(errorCodes.name())
            .build();
    return ErrorDetails.builder()
        .timestamp(EmailUtils.getDateWithTimeAsString(new Date()))
        .responseCode(status.value())
        .details(errorDeveloperDetails)
        .message(displayMsg)
        .build();
  }
}
