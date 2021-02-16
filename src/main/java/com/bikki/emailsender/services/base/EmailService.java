package com.bikki.emailsender.services.base;

import com.bikki.emailsender.models.Email;
import com.bikki.emailsender.pojos.responses.ResSendEmail;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public interface EmailService {
  ResSendEmail sendEmail(
      String to, String subject, String bodyText, ArrayList<MultipartFile> attachFile)
      throws UnsupportedEncodingException, MessagingException;

  List<Email> getAllEmails();
}
