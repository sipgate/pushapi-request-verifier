package com.sipgate.pushapi.verifier;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class KeyTest {
  @Test
  public void testKeyInitialization() {
    assertThat(Key.getPublicKey()).isNotNull();
  }
}
