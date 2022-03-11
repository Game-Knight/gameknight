package Utils;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

import javax.crypto.Cipher;

import Exceptions.CryptographyException;

public class EncryptionUtils {

    public static String encrypt(String valueToEncrypt) throws CryptographyException {
        try {
            // This encryption method comes from:
            // https://www.tutorialspoint.com/java_cryptography/java_cryptography_decrypting_data.htm
            Signature sign = Signature.getInstance("SHA256withRSA");

            // TODO: This encryption method is too slow, it's not a good way to encrypt the API key like I was hoping.
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] input = valueToEncrypt.getBytes();
            cipher.update(input);
            byte[] cipherText = cipher.doFinal();

            return new String(cipherText);
        }
        catch (Exception ex) {
            throw new CryptographyException("There was an error encrypting the provided value: " + ex.getMessage());
        }
    }

    public static String decrypt(String valueToDecrypt) throws CryptographyException {
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PrivateKey privateKey = keyPair.getPrivate();

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] input = valueToDecrypt.getBytes();
            byte[] decipheredText = cipher.doFinal(input);

            return new String(decipheredText);
        }
        catch (Exception ex) {
            throw new CryptographyException("There was an error decrypting the provided value: " + ex.getMessage());
        }
    }
}
