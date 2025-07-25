package org.neshan.hotelbooking.security;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.neshan.hotelbooking.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TwoFactorAuthService {
    @Value("${app.name}")
    String appName;

    private static final int SECRET_SIZE = 10;
    private static final int WINDOW_SIZE = 3;
    private static final int CODE_DIGITS = 6;

    public String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[SECRET_SIZE];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    public String getGoogleAuthenticatorBarCode(String secretKey, String account) {
        try {
            String encodedKey = URLEncoder.encode(secretKey, StandardCharsets.UTF_8);
            String encodedAccount = URLEncoder.encode(account, StandardCharsets.UTF_8);
            String encodedIssuer = URLEncoder.encode(appName, StandardCharsets.UTF_8);
            return "otpauth://totp/"
                    + encodedIssuer + ":" + encodedAccount
                    + "?secret=" + encodedKey
                    + "&issuer=" + encodedIssuer
                    + "&algorithm=SHA1&digits=" + CODE_DIGITS + "&period=30";
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create Google Authenticator barcode", e);
        }
    }

    public String generateQrCodeImage(String barCodeData) {
        try {
            QRCodeWriter barcodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = barcodeWriter.encode(barCodeData, BarcodeFormat.QR_CODE, 200, 200);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate QR code", e);
        }
    }

    public boolean validateOtp(String secretKey, String code) {
        if (code == null || code.length() != CODE_DIGITS) {
            return false;
        }
        
        long timeWindow = System.currentTimeMillis() / 30000;
        
        try {
            Base32 base32 = new Base32();
            byte[] bytes = base32.decode(secretKey);
            
            for (int i = -WINDOW_SIZE; i <= WINDOW_SIZE; ++i) {
                String hash = generateTOTP(bytes, timeWindow + i);
                if (hash.equals(code)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        
        return false;
    }

    private String generateTOTP(byte[] key, long time) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = time;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }

        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);

        int offset = hash[hash.length - 1] & 0xF;
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }

        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= Math.pow(10, CODE_DIGITS);

        return String.format("%0" + CODE_DIGITS + "d", truncatedHash);
    }
}