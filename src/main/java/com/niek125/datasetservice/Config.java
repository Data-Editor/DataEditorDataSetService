package com.niek125.datasetservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niek125.datasetservice.handlers.FileHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

import static com.niek125.datasetservice.utils.PemUtils.readPublicKeyFromFile;

@Configuration
public class Config {
    @Bean
    public FileHandler fileHandler(){
        return new FileHandler();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public JWTVerifier jwtVerifier() throws IOException {
        Algorithm algorithm = Algorithm.RSA512((RSAPublicKey) readPublicKeyFromFile("src/main/resources/PublicKey.pem", "RSA"), null);
        return JWT.require(algorithm).withIssuer("data-editor-token-service").build();
    }
}
