package pwcracking;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//this class is for computing hash value (SHA256) of a string
public class HashFunction {

  public static String getSHA256Hash(String word) {
    String hashValue = null;
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] encodedHash = digest.digest(word.getBytes(StandardCharsets.UTF_8));
      hashValue = bytesToHex(encodedHash);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return hashValue;
  }

  private static String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (int i = 0; i < hash.length; i++) {
      String hex = Integer.toHexString(0xff & hash[i]);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

}
