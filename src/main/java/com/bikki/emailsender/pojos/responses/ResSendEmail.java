package com.bikki.emailsender.pojos.responses;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Builder
public class ResSendEmail implements Serializable {

  private String status;

  public ResSendEmail(String status) {
    this.status = status;
  }
}
