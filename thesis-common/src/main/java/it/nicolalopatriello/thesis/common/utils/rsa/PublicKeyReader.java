package it.nicolalopatriello.thesis.common.utils.rsa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Generate a 2048-bit RSA private key
 * <p>
 * $openssl genrsa -out private_key.pem 2048
 * <p>
 * Convert private Key to PKCS#8 format (so Java can read it)
 * <p>
 * $openssl pkcs8 -topk8 -inform PEM -outform DER -in private_key.pem -out private_key.der -nocrypt
 * <p>
 * Output public key portion in DER format (so Java can read it)
 * <p>
 * $openssl rsa -in private_key.pem -pubout -outform DER -out public_key.der
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PublicKeyReader {

    public static final String ALGORITHM = "RSA";

    /**
     * @param filename file .der generate with previous algorithm: http://codeartisan.blogspot.it/2009/05/public-key-cryptography-in-java.html
     * @return
     * @throws Exception
     */
    public static PublicKey get(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (StringUtils.isBlank(filename)) throw new FileNotFoundException(null);
        return get(new File(filename));
    }

    public static PublicKey get(File filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (filename == null || !filename.exists())
            throw new FileNotFoundException(filename != null ? filename.getAbsolutePath() : null);

        byte[] keyBytes = Files.readAllBytes(filename.toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        return kf.generatePublic(spec);
    }
}
