package com.sipgate.pushapi.util;

import static org.apache.commons.lang3.StringUtils.defaultString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;
import org.apache.commons.lang3.StringUtils;

public class HttpRequest {

  private String requestLine;
  private final Hashtable<String, String> headers;
  private final StringBuffer body;

  private HttpRequest() {
    headers = new Hashtable<>();
    body = new StringBuffer();
  }

  public static HttpRequest parse(final String request) {
    final var result = new HttpRequest();
    try (final var reader = new BufferedReader(new StringReader(request))) {
      result.setRequestLine(reader.readLine());
      parseHeaders(result, reader);
      parseBody(result, reader);
      return result;
    } catch (IOException e) {
      throw new RuntimeException("failed to parse request", e);
    }
  }

  private static void parseBody(HttpRequest result, BufferedReader reader) throws IOException {
    var bodyLine = reader.readLine();
    while (bodyLine != null) {
      result.appendMessageBody(bodyLine);
      bodyLine = reader.readLine();
    }
  }

  private static void parseHeaders(HttpRequest result, BufferedReader reader) throws IOException {
    var header = defaultString(reader.readLine());
    while (header.length() > 0) {
      result.appendHeaderParameter(header);
      header = reader.readLine();
    }
  }

  public String getRequestLine() {
    return requestLine;
  }

  private void setRequestLine(String requestLine) {
    if (requestLine == null || requestLine.length() == 0) {
      throw new RuntimeException("failed to parse request line" + requestLine);
    }
    this.requestLine = requestLine;
  }

  private void appendHeaderParameter(String header) {
    if (!header.contains(":")) {
      throw new RuntimeException("failed to parse header line " + header);
    }
    final var chunks = header.split(":");
    headers.put(chunks[0], chunks[1]);
  }

  public byte[] getBody() {
    return body.toString().getBytes();
  }

  private void appendMessageBody(String bodyLine) {
    body.append(bodyLine);
  }

  public String getHeader(String header) {
    return StringUtils.trimToEmpty(headers.get(header));
  }
}
