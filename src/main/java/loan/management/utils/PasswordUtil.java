package loan.management.utils;

import io.quarkus.elytron.security.common.BcryptUtil;

public class PasswordUtil {

    public static String hash(String password) {
        return BcryptUtil.bcryptHash(password);
    }

    public static boolean verify(String password, String hash) {
        return BcryptUtil.matches(password, hash);
    }
}