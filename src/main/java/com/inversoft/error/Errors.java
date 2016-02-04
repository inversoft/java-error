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


import org.primeframework.json.ToString;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Standard error domain object that can also be used as the response from an API call.
 *
 * @author Brian Pontarelli
 */
public class Errors {
  public final Map<String, List<Error>> fieldErrors = new LinkedHashMap<>();

  public final List<Error> generalErrors = new LinkedList<>();

  /**
   * Add all of {@code otherErrors} field and general errors to this object
   *
   * @param otherErrors can be null
   * @return this
   */
  public Errors add(Errors otherErrors) {
    if (otherErrors != null) {
      fieldErrors.putAll(otherErrors.fieldErrors);
      generalErrors.addAll(otherErrors.generalErrors);
    }
    return this;
  }

  public Errors addFieldError(String field, String code, String message, Object... values) {
    List<Error> errors = fieldErrors.get(field);
    if (errors == null) {
      errors = new LinkedList<>();
      fieldErrors.put(field, errors);
    }

    errors.add(new Error(code, message, values));

    return this;
  }

  public Errors addGeneralError(String code, String message, Object... values) {
    generalErrors.add(new Error(code, message, values));
    return this;
  }

  /**
   * Search through all errors, general and field, and if any error.code is prefixed by the codePrefix, passed in,
   * return true. Otherwise return false.
   * <p>
   * This is a little loose, making the caller know which code prefixes the Validator is using, an enum or something
   * might work nicely... for now keeping this.
   */
  public boolean containsError(String codePrefix) {
    for (Error error : generalErrors) {
      if (error.code.startsWith(codePrefix)) {
        return true;
      }
    }
    for (List<Error> errors : fieldErrors.values()) {
      for (Error error : errors) {
        if (error.code.startsWith(codePrefix)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * This is named <strong>empty</strong> because <strong>isEmpty</strong> is a JavaBean property name and Jackson
   * serializes that out.
   *
   * @return True if this Errors is empty, false otherwise.
   */
  public boolean empty() {
    return generalErrors.isEmpty() && fieldErrors.isEmpty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Errors errors = (Errors) o;
    return fieldErrors.equals(errors.fieldErrors) && generalErrors.equals(errors.generalErrors);
  }

  @Override
  public int hashCode() {
    int result = generalErrors.hashCode();
    result = 31 * result + fieldErrors.hashCode();
    return result;
  }

  /**
   * @return the total count of all errors. All field errors and general errors
   */
  public int size() {
    return generalErrors.size() + fieldErrors.values().stream().mapToInt(List::size).sum();
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
