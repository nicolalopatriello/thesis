package it.nicolalopatriello.thesis.common.utils;

import it.nicolalopatriello.thesis.common.exception.DecryptionException;
import it.nicolalopatriello.thesis.common.exception.EncryptionException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Log4j
@AllArgsConstructor
public class DataEncryptor {

    private IvParameterSpec ivspec;
    private SecretKeySpec secretKeySpec;
    private Cipher cipher;

    public static DataEncryptor from(String secret, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        return new DataEncryptor(ivspec, secretKeySpec, cipher);
    }


    public String encrypt(String plainText) throws EncryptionException {
        if (plainText == null)
            return null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new EncryptionException();
        }
    }

    public String decrypt(String encodedText) throws DecryptionException {
        if (encodedText == null)
            return null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encodedText)));
        } catch (Exception e) {
            throw new DecryptionException();
        }
    }

}
