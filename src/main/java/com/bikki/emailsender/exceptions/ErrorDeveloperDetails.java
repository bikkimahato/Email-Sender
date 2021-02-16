package com.bikki.emailsender.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDeveloperDetails implements Serializable {

  private String applicationError;
  private String applicationErrorCode;
  private String applicationErrorMessage;

  public ErrorDeveloperDetails(String description) {
    JsonObject jsonObject = new Gson().fromJson(description, JsonObject.class);
    if (jsonObject.isJsonObject()) {
      this.applicationError = jsonObject.get("applicationError").getAsString();
      this.applicationErrorCode = jsonObject.get("applicationErrorCode").getAsString();
      this.applicationErrorMessage = jsonObject.get("applicationErrorMessage").getAsString();
    }
  }
}
