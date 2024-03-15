import java.security.SecureRandom;
import java.util.Base64;

public class JWTCodeGen {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32]; // Generate a 256-bit key
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        System.out.println("JWT_SECRET_KEY=" + encodedKey);
    }
}
