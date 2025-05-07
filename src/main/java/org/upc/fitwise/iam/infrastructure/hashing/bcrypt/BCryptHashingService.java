package org.upc.fitwise.iam.infrastructure.hashing.bcrypt;

import org.upc.fitwise.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {

}
