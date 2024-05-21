package vn.molu.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

// Class  DesEncrypterUtils chứa các phương thức liên quan đến mã hóa và giải mã chuỗi sử dụng thuật toán DES
public final class DesEncrypterUtils {
    private transient final Log log = LogFactory.getLog(DesEncrypterUtils.class);
    private static DesEncrypterUtils instance = new DesEncrypterUtils();
    private static final String SECURITY_PHASE = "vmsReport";
    private SecretKey key;
    private AlgorithmParameterSpec paramSpec;

    public static DesEncrypterUtils getInstance() {
        return instance;
    }

    // Phương thức khởi tạo của lớp DesEncrypterUtils, tạo khóa và tham số cho thuật toán mã hóa DES.
    private DesEncrypterUtils() {
        try {
            // 8-bytes Salt
            byte[] salt = {(byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x34, (byte) 0xE3,
                    (byte) 0x03};

            int iterationCount = 19;
            KeySpec keySpec = new PBEKeySpec(SECURITY_PHASE.toCharArray(), salt, iterationCount);

            key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            paramSpec = new PBEParameterSpec(salt, iterationCount);

        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        } catch (InvalidKeySpecException e) {
            log.error(e.getMessage(), e);
        }
    }

    @SuppressWarnings("hiding")
    // encrypt(String str): Mã hóa một chuỗi ký tự sử dụng thuật toán DES.
    public String encrypt(String str) {
        try {
            Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            byte[] utf8 = str.getBytes("UTF8");
            byte[] enc = ecipher.doFinal(utf8);
            return new BASE64Encoder().encode(enc);
        } catch (BadPaddingException e) {
            log.error(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        } catch (java.io.IOException e) {
            log.error(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    // decrypt(String str): Giải mã một chuỗi ký tự đã được mã hóa sử dụng thuật toán DES.
    public String decrypt(String str) {
        try {
            Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

            byte[] dec = new BASE64Decoder().decodeBuffer(str);
            byte[] utf8 = dcipher.doFinal(dec);
            return new String(utf8, "UTF8");
        } catch (BadPaddingException e) {
            log.error(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            log.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        } catch (java.io.IOException e) {
            log.error(e.getMessage());
        } catch (InvalidKeyException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            log.error(e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    //Testing
    public static void main(String[] args) {
        try {
            System.out.println("aaaaaaaaaaaa");
            System.out.println(getInstance().encrypt("vms@kpi"));
            System.out.println(getInstance().decrypt("GSBObb9no5U="));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
