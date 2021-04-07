package com.sipgate.pushapi.verifier;

import static com.sipgate.pushapi.util.Resource.readResource;
import static org.slf4j.LoggerFactory.getLogger;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;
import org.slf4j.Logger;

public class Key {

  private static final Logger LOGGER = getLogger(Key.class);

  private static final String PUBLIC_KEY_RESOURCE = "/fastagi.pub";

  private static PublicKey key;

  public static PublicKey getPublicKey() {
    if (key == null) {
      key =
          readPublicKey()
              .orElseThrow(
                  () ->
                      new RuntimeException(
                          "failed to read public key from " + PUBLIC_KEY_RESOURCE));
    }
    return key;
  }

  private static Optional<PublicKey> readPublicKey() {
    try {
      final String resource = readResource(PUBLIC_KEY_RESOURCE);
      final var rawKey = resource.replaceAll("----.*?----*", "").replaceAll("\n", "");
      final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rawKey));
      final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      return Optional.of(keyFactory.generatePublic(keySpec));
    } catch (Exception e) {
      LOGGER.warn("failed to initialize public key", e);
      return Optional.empty();
    }
  }
}
