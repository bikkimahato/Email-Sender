package com.bikki.emailsender.controllers;

import com.bikki.emailsender.constants.EmailConstants;
import com.bikki.emailsender.models.Email;
import com.bikki.emailsender.pojos.BaseResponse;
import com.bikki.emailsender.pojos.responses.ResSendEmail;
import com.bikki.emailsender.services.base.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1")
public class EmailController {

  private final EmailService emailService;

  @Autowired
  public EmailController(EmailService emailService) {
    this.emailService = emailService;
  }

  @PostMapping(
      value = "/email",
      consumes = "multipart/form-data",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BaseResponse<ResSendEmail>> sendEmail(
      @RequestParam String to,
      @RequestParam String subject,
      @RequestParam String bodyText,
      @RequestParam(name = "attachFile", required = false) ArrayList<MultipartFile> attachFile)
      throws MessagingException, UnsupportedEncodingException {

    return new ResponseEntity<>(
        new BaseResponse<>(
            EmailConstants.RESPONSE_STATUS.success.name(),
            emailService.sendEmail(to, subject, bodyText, attachFile)),
        HttpStatus.CREATED);
  }

  @GetMapping(value = "email")
  public ResponseEntity<BaseResponse<List<Email>>> getAllEmails() {
    return new ResponseEntity<>(
        new BaseResponse<>(
            EmailConstants.RESPONSE_STATUS.success.name(), emailService.getAllEmails()),
        HttpStatus.OK);
  }
}
