package com.gnomeshift.springtaskmanager.security.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private final int jwtExpirationMs;
    private final Key privateKey;
    private final Key publicKey;
    private static final String ALGORITHM = "RSA";

    public JwtUtils(@Value("${jwt.expirationMs}") int jwtExpirationMs,
                    @Value("${jwt.privateKeyPath}") String privateKeyPath,
                    @Value("${jwt.publicKeyPath}") String publicKeyPath) {
        this.jwtExpirationMs = jwtExpirationMs;

        try {
            privateKey = getPrivateKey(privateKeyPath);
            publicKey = getPublicKey(publicKeyPath);
        }
        catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("Error when loading keys: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to load keys", e);
        }
    }

    private PrivateKey getPrivateKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            String privateKeyString = new String(Files.readAllBytes(Paths.get(path)));
            privateKeyString = privateKeyString.replace("-----BEGIN PRIVATE KEY-----", "");
            privateKeyString = privateKeyString.replace("-----END PRIVATE KEY-----", "");
            privateKeyString = privateKeyString.replaceAll("\\s+", "");

            byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM);

            return kf.generatePrivate(keySpec);
        }
        catch (Exception e) {
            logger.error("Exception while reading private key: {}", e.getMessage(), e);
            throw e;
        }
    }

    private PublicKey getPublicKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            String publicKeyString = new String(Files.readAllBytes(Paths.get(path)));
            publicKeyString = publicKeyString.replace("-----BEGIN PUBLIC KEY-----", "");
            publicKeyString = publicKeyString.replace("-----END PUBLIC KEY-----", "");
            publicKeyString = publicKeyString.replaceAll("\\s+", "");

            byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM);

            return kf.generatePublic(keySpec);
        }
        catch (Exception e) {
            logger.error("Exception while reading public key: {}", e.getMessage(), e);
            throw e;
        }
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder().subject(userPrincipal.getUsername()).issuedAt(now).expiration(expiryDate)
                .signWith(privateKey).compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().verifyWith((PublicKey) publicKey).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().verifyWith((PublicKey) publicKey).build().parse(token);
            return true;
        }
        catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        }
        catch (ExpiredJwtException e) {
            logger.error("JWT token has expired: {}", e.getMessage());
        }

        return false;
    }
}
