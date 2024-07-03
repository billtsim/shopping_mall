package com.a88.utils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class VerificationCodeUtil {

    private static final SecureRandom random = new SecureRandom();
    private static final Map<String, String> verificationCodes = new HashMap<>();

    public static String generateCode() {
        return String.format("%05d", random.nextInt(100000));
    }

    public static void storeCode(String email, String code) {
        verificationCodes.put(email, code);
    }

    public static String getCode(String email) {
        return verificationCodes.get(email);
    }

    public static boolean validateCode(String email, String code) {
        String storedCode = verificationCodes.get(email);
        return storedCode != null && storedCode.equals(code);
    }

    public static void removeCode(String email) {
        verificationCodes.remove(email);
    }
}
