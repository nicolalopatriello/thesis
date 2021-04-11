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
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

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
public class PrivateKeyReader {

    public static PrivateKey get(String filename) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        if (StringUtils.isBlank(filename)) throw new FileNotFoundException(null);
        return get(new File(filename));
    }

    /**
     * @param filename filename .der generate with previous algorithm: http://codeartisan.blogspot.it/2009/05/public-key-cryptography-in-java.html
     * @return
     * @throws Exception
     */
    public static PrivateKey get(File filename)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        if (filename == null || !filename.exists())
            throw new FileNotFoundException(filename != null ? filename.getAbsolutePath() : null);
        byte[] keyBytes = Files.readAllBytes(filename.toPath());

        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
