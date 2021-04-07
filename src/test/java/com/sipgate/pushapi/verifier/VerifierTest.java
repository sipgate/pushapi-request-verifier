package com.sipgate.pushapi.verifier;

import static com.sipgate.pushapi.verifier.Verifier.verify;
import static org.assertj.core.api.Assertions.assertThat;

import com.sipgate.pushapi.util.HttpRequest;
import com.sipgate.pushapi.util.Resource;
import org.junit.jupiter.api.Test;

class VerifierTest {

  final HttpRequest request = HttpRequest.parse(Resource.readResource("/request"));

  @Test
  public void testVerifier() {
    assertThat(verify(request.getBody(), request.getHeader("X-Sipgate-Signature")))
        .isTrue();
  }
}
