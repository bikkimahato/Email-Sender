package com.bikki.emailsender.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/** Base Response for all APIs */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {

  /** status */
  private String status;

  /** Response Entity */
  private T data;
}
