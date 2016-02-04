/*
 * Copyright (c) 2013-2015, Inversoft Inc., All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package com.inversoft.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.primeframework.json.JacksonConstructor;
import org.primeframework.json.ToString;

import java.util.Arrays;

/**
 * Defines an error.
 *
 * @author Brian Pontarelli
 */
public class Error {
  public String code;

  public String message;

  @JsonIgnore
  public Object[] values;

  /**
   * Constructor used for Jackson.
   */
  @JacksonConstructor
  public Error() {
  }

  public Error(String code, String message, Object... values) {
    this.code = code;
    this.message = message;
    this.values = values;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Error error = (Error) o;

    return code.equals(error.code) &&
        (message != null ? message.equals(error.message) : error.message == null) &&
        Arrays.equals(values, error.values);
  }

  @Override
  public int hashCode() {
    int result = code.hashCode();
    result = 31 * result + (message != null ? message.hashCode() : 0);
    result = 31 * result + Arrays.hashCode(values);
    return result;
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
