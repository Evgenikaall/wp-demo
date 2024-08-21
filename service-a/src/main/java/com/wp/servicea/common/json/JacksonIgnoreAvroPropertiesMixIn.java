package com.wp.servicea.common.json;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Helps to ignore AvroSpecific data during serialization/deserialization processes
 */
public abstract class JacksonIgnoreAvroPropertiesMixIn {

  @JsonIgnore
  public abstract org.apache.avro.Schema getSchema();

  @JsonIgnore
  public static org.apache.avro.Schema getClassSchema(){
    return null;
  }

  @JsonIgnore
  public abstract org.apache.avro.specific.SpecificData getSpecificData();
}