
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {

    public static final int DEFAULT_LEN = 32;

    private static final char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final String[] hashTypes = new String[]{"MD2", "MD5", "SHA1", "SHA-256", "SHA-384", "SHA-512"};

    private static MessageDigest getEncryptByName(String algorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(algorithm);
    }

    public static String md2(String str) {
        MessageDigest md;
        try {
            md = getEncryptByName(hashTypes[0]);
            return toHexString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String md5(String str) {
        MessageDigest md;
        try {
            md = getEncryptByName(hashTypes[1]);
            return toHexString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String md5(String str, String charsetName) {
        MessageDigest md;
        try {
            md = getEncryptByName(hashTypes[1]);
            return toHexString(md.digest(str.getBytes(charsetName)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sha1(String str) {
        MessageDigest md;
        try {
            md = getEncryptByName(hashTypes[2]);
            return toHexString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sha256(String str) {
        MessageDigest md;
        try {
            md = getEncryptByName(hashTypes[3]);
            return toHexString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sha384(String str) {
        MessageDigest md;
        try {
            md = getEncryptByName(hashTypes[4]);
            return toHexString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sha512(String str) {
        MessageDigest md;
        try {
            md = getEncryptByName(hashTypes[5]);
            return toHexString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
            sb.append(hexChar[b[i] & 0x0f]);
        }
        return sb.toString();
    }

}
