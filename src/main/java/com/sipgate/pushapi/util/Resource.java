package com.sipgate.pushapi.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Resource {
  public static String readResource(final String resource) {
    try (final InputStream input = Resource.class.getResourceAsStream(resource);
        final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
      int length;
      final byte[] buffer = new byte[512];
      while ((length = input.read(buffer)) > 0) {
        output.write(buffer, 0, length);
      }
      return output.toString();
    } catch (final Exception e) {
      throw new RuntimeException("failed to read resource", e);
    }
  }
}
