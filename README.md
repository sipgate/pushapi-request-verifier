# pushapi-request-verifier

Sipgate offers a push API to all customers. With this api, all relevant telephony events such as incoming or outgoing calls can be sent via HTTP requsts to any customer system. 

To ensure that no unwanted requests are processed, requests can use a signature provided with each request to ensure the authenticity of the request. 
The signature is supplied via the X-Sipgate-Signature header. The complete body of the respective request is signed. 

This project aims at verifying sipgate push-api requests in a simple and satisfying way. 

## usage
The Verfier is very easy to use. A complete example can be seen in VerifierTest. 
Basically a verification looks like this: 
```
final byte[] body = "...".getBytes();
final String signature = "...";

final boolean success = Verifier.verify(body, signature);
