package com.bikki.emailsender.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Email implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "mail_body_text", columnDefinition = "TEXT")
  private String mailBodyText;

  @Column(name = "mail_recipient_emails")
  private String mailRecipientEmails;

  @Column(name = "subject", columnDefinition = "TEXT")
  private String subject;

  @Column(name = "response", columnDefinition = "TEXT")
  private String response;

  @CreationTimestamp private LocalDateTime createdAt;

  @UpdateTimestamp private LocalDateTime modifiedAt;

  public Email(String to, String subject, String bodyText) {
    this.mailBodyText = bodyText;
    this.subject = subject;
    this.mailRecipientEmails = to;
  }
}
