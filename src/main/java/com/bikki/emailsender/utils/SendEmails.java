package com.bikki.emailsender.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Objects;

@Component
@Slf4j
public class SendEmails {

  @Autowired private final MailUtil mailUtil;

  @Autowired
  public SendEmails(MailUtil mailUtil) {
    this.mailUtil = mailUtil;
  }

  private void setEmailFields(String to, String subject, String bodyText, MimeMessageHelper helper)
      throws MessagingException {
    helper.setSubject(subject);
    helper.setText(bodyText, true);
    helper.setTo(InternetAddress.parse(to));
  }

  public void sendSimpleMail(String to, String subject, String bodyText) {
    mailUtil
        .javaMailService()
        .send(
            mimeMessage -> {
              // Enable the multipart flag!
              MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
              setEmailFields(to, subject, bodyText, helper);
            });
  }

  public void sendSimpleMailWithAttachment(
      String to, String subject, String bodyText, ArrayList<MultipartFile> attachment) {
    mailUtil
        .javaMailService()
        .send(
            mimeMessage -> {
              // Enable the multipart flag!
              MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
              setEmailFields(to, subject, bodyText, helper);

              for (MultipartFile attachFile : attachment) {
                if ((attachFile != null) && (attachFile.getSize() > 0)) {
                  log.info("\nAttachment Name?= " + attachFile.getOriginalFilename() + "\n");
                  helper.addAttachment(
                      Objects.requireNonNull(attachFile.getOriginalFilename()), attachFile);
                }
              }
            });
  }
}
