package com.sipgate.pushapi.verifier;

import static com.sipgate.pushapi.verifier.Key.getPublicKey;
import static org.slf4j.LoggerFactory.getLogger;

import java.security.Signature;
import java.util.Base64;
import org.slf4j.Logger;

public class Verifier {

  private static final Logger LOGGER = getLogger(Verifier.class);

  public static boolean verify(byte[] body, String signature) {
    try {
      final Signature publicSignature = Signature.getInstance("SHA256withRSA");
      publicSignature.initVerify(getPublicKey());
      publicSignature.update(body);

      final byte[] signatureBytes = Base64.getDecoder().decode(signature);

      return publicSignature.verify(signatureBytes);
    } catch (Exception e) {
      LOGGER.warn("failed to verify signature: {}", e.getMessage());
      return false;
    }
  }
}
