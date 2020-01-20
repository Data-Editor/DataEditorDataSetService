package com.niek125.datasetservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

import static com.niek125.datasetservice.utils.PemUtils.readPublicKeyFromFile;

@Configuration
public class Config {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Value("${com.niek125.publickey}")
    private String publickey;

    @Value("${com.niek125.allowed-token-signer}")
    private String tokenSigner;

    @Bean
    public JWTVerifier jwtVerifier() throws IOException {
        final Algorithm algorithm = Algorithm.RSA512((RSAPublicKey) readPublicKeyFromFile(publickey, "RSA"), null);
        return JWT.require(algorithm).withIssuer(tokenSigner).build();
    }

}
