/*
 * Copyright (c) 2013-2016, Inversoft Inc., All Rights Reserved
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

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inversoft.json.JacksonConstructor;
import com.inversoft.json.ToString;

/**
 * Defines an error.
 *
 * @author Brian Pontarelli
 */
public class Error {
  public String code;

  public Map<String, Object> data;

  public String message;

  @JsonIgnore
  public Object[] values;

  /**
   * Constructor used for Jackson.
   */
  @JacksonConstructor
  public Error() {
  }

  public Error(String code, String message, Map<String, Object> data, Object... values) {
    this.code = code;
    this.data = data;
    this.message = message;
    this.values = values;
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
    return Objects.equals(code, error.code) && Objects.equals(data, error.data) && Objects.equals(message, error.message) && Arrays.equals(values, error.values);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(code, data, message);
    result = 31 * result + Arrays.hashCode(values);
    return result;
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
