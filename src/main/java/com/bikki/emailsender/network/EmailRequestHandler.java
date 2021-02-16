package com.bikki.emailsender.network;

import com.bikki.emailsender.constants.EmailConstants;
import com.bikki.emailsender.constants.EmailExceptionConstants;
import com.bikki.emailsender.exceptions.InternalServerException;
import com.bikki.emailsender.models.Email;
import com.bikki.emailsender.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Slf4j
@Component
public class EmailRequestHandler {
  private boolean crunchifyEmailValidator(String email) {
    boolean isValid = false;
    try {
      InternetAddress internetAddress = new InternetAddress(email);
      internetAddress.validate();
      isValid = true;
    } catch (AddressException e) {
      log.info(e.getMessage());
    }
    return isValid;
  }

  public void checkEmail(
      String to,
      String subject,
      String bodyText,
      Email emailAudit,
      EmailRepository emailRepository) {
    String[] emails = to.split(",");
    for (String email : emails) {
      if (!crunchifyEmailValidator(email)) {
        emailAudit.setResponse(EmailConstants.RESPONSE_STATUS.failure.name());
        emailRepository.save(emailAudit);
        throw new InternalServerException(
            EmailExceptionConstants.INVALID_EMAIL,
            EmailExceptionConstants.EMAIL_APP_ERROR_CODES.INVALID_EMAIL);
      }
    }
    if (subject.isEmpty() || bodyText.isEmpty()) {
      emailAudit.setResponse(EmailConstants.RESPONSE_STATUS.failure.name());
      emailRepository.save(emailAudit);
      throw new InternalServerException(
          EmailExceptionConstants.REQ_BODY_MISSING,
          EmailExceptionConstants.EMAIL_APP_ERROR_CODES.INVALID_REQ_BODY);
    }
  }
}
