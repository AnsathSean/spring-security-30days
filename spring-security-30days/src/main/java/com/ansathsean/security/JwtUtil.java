package com.ansathsean.security;


import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class JwtUtil {


    private static final String SECRET = "mySecretKey";

    // 生成 JWT
    public static String generateToken(Map<String, Object> payload) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Header
        String headerJson = mapper.writeValueAsString(Map.of("alg", "HS256", "typ", "JWT"));
        String headerEncoded = base64UrlEncode(headerJson.getBytes(StandardCharsets.UTF_8));

        // Payload
        String payloadJson = mapper.writeValueAsString(payload);
        String payloadEncoded = base64UrlEncode(payloadJson.getBytes(StandardCharsets.UTF_8));

        // 簽名
        String data = headerEncoded + "." + payloadEncoded;
        String signature = hmacSha256(data, SECRET);

        return data + "." + signature;
    }

    // 驗證 JWT
    public static boolean validateToken(String token) throws Exception {
        String[] parts = token.split("\\.");
        if (parts.length != 3) return false;

        String data = parts[0] + "." + parts[1];
        String signature = parts[2];

        String expectedSignature = hmacSha256(data, SECRET);

        return expectedSignature.equals(signature);
    }

    private static String base64UrlEncode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private static String hmacSha256(String data, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        return base64UrlEncode(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }
}
