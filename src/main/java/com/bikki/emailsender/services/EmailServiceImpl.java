package com.bikki.emailsender.services;

import com.bikki.emailsender.constants.EmailConstants;
import com.bikki.emailsender.models.Email;
import com.bikki.emailsender.network.EmailRequestHandler;
import com.bikki.emailsender.pojos.responses.ResSendEmail;
import com.bikki.emailsender.repository.EmailRepository;
import com.bikki.emailsender.services.base.EmailService;
import com.bikki.emailsender.utils.SendEmails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

  private final SendEmails sendEmails;

  private final EmailRepository emailRepository;

  private final EmailRequestHandler emailRequestHandler;

  @Autowired
  public EmailServiceImpl(
      SendEmails sendEmails,
      EmailRepository emailRepository,
      EmailRequestHandler emailRequestHandler) {
    this.sendEmails = sendEmails;
    this.emailRepository = emailRepository;
    this.emailRequestHandler = emailRequestHandler;
  }

  @Override
  public List<Email> getAllEmails() {
    List<Email> emailList = new ArrayList<>();
    emailList = emailRepository.findAll();
    return emailList;
  }

  @Override
  public ResSendEmail sendEmail(
      String to, String subject, String bodyText, ArrayList<MultipartFile> attachFile)
      throws MessagingException {

    Email email = new Email(to, subject, bodyText);
    email = emailRepository.save(email);

    emailRequestHandler.checkEmail(to, subject, bodyText, email, emailRepository);

    if ((attachFile != null) && (attachFile.size() > 0)) {
      sendEmails.sendSimpleMailWithAttachment(to, subject, bodyText, attachFile);
    } else {
      sendEmails.sendSimpleMail(to, subject, bodyText);
    }

    ResSendEmail resSendEmail = new ResSendEmail(EmailConstants.RESPONSE_STATUS.success.name());
    email.setResponse(EmailConstants.RESPONSE_STATUS.success.name());
    emailRepository.save(email);

    return resSendEmail;
  }
}
