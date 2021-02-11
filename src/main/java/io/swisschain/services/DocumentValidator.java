package io.swisschain.services;

import io.swisschain.crypto.asymmetric.AsymmetricEncryptionService;
import io.swisschain.domain.document.GuardianKey;
import io.swisschain.domain.document.SignatureValidationResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DocumentValidator {
    private static final Logger logger = LogManager.getLogger();

    private final AsymmetricEncryptionService asymmetricEncryptionService;
    private final GuardianKey guardianKey;

    public DocumentValidator(
            AsymmetricEncryptionService asymmetricEncryptionService,
            GuardianKey guardianKey) {
        this.asymmetricEncryptionService = asymmetricEncryptionService;
        this.guardianKey = guardianKey;
    }

    public SignatureValidationResult Validate(String document, String signature) {
        try {
            var isValid =
                    asymmetricEncryptionService.verifySignature(
                            document.getBytes(StandardCharsets.UTF_8),
                            Base64.getDecoder().decode(signature),
                            guardianKey.getPublicKey());

            if (isValid) {
                return SignatureValidationResult.CreateValid();
            }

            return SignatureValidationResult.CreateInvalid("Wrong document signature");

        } catch (IOException exception) {
            logger.error("An error occurred while verifying document signature.", exception);
            return SignatureValidationResult.CreateInvalid("Unknown error");
        }
    }
}
