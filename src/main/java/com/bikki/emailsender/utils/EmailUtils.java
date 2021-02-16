package com.bikki.emailsender.utils;

import com.bikki.emailsender.constants.EmailConstants;
import com.bikki.emailsender.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class EmailUtils {
  public static String getDateWithTimeAsString(Date date) {
    DateFormat dateFormat = new SimpleDateFormat(EmailConstants.EMAIL_DATE_FORMAT_WITH_TIME);
    return dateFormat.format(date);
  }

  public static String validateMobileNumber(String mobileNumber, String message) {
    checkIfStringEmpty(mobileNumber, message);
    Pattern pattern = Pattern.compile("[6-9][0-9]{9}");
    Matcher matcher = pattern.matcher(mobileNumber);
    if (!matcher.find() || !matcher.group().equals(mobileNumber)) {
      log.error(message + mobileNumber);
      throw new BadRequestException(message + mobileNumber);
    } else {
      return mobileNumber;
    }
  }

  public static String checkIfStringEmpty(String string, String message) {
    if (StringUtils.isEmpty(string)) {
      log.error(message);
      throw new BadRequestException(message);
    }
    return string;
  }

  public static <T> List<T> checkIfListNull(List<T> t, String message) {
    if (t == null || t.isEmpty()) {
      log.error(message);
      throw new BadRequestException(message);
    }
    return t;
  }

  public static String generateRandomUUID() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }
}
