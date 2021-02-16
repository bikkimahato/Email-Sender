package com.bikki.emailsender.exceptions;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmailExchangeErrorHandler implements ResponseErrorHandler {
  private static final Logger log = LoggerFactory.getLogger(EmailExchangeErrorHandler.class);
  @NotNull private static final String goodStatus = "OK,CREATED,ACCEPTED";
  private final List acceptableStatus;

  public EmailExchangeErrorHandler() {
    acceptableStatus =
        Arrays.stream(goodStatus.split(",")).map(HttpStatus::valueOf).collect(Collectors.toList());
  }

  @Override
  public void handleError(@NotNull ClientHttpResponse response) throws IOException {
    String body = StreamUtils.copyToString(response.getBody(), Charset.defaultCharset());
    log.error("Response error: {} {}", response.getStatusCode(), body);
    Gson gson = new Gson();
    ErrorDetails error = gson.fromJson(body, ErrorDetails.class);
    throw new EmailGenericException(error.getMessage(), response.getStatusCode());
  }

  @Override
  public boolean hasError(@NotNull ClientHttpResponse response) throws IOException {
    return !acceptableStatus.contains(response.getStatusCode());
  }
}
