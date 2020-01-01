package com.niek125.datasetservice.controllers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niek125.datasetservice.models.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@RestController
@RequestMapping("/data")
public class DataSetController {
    private final Logger logger = LoggerFactory.getLogger(DataSetController.class);
    private final JWTVerifier jwtVerifier;
    private final ObjectMapper objectMapper;
    @Value("${com.niek125.filedump}")
    private String filedir;

    @Autowired
    public DataSetController(JWTVerifier jwtVerifier, ObjectMapper objectMapper) {
        this.jwtVerifier = jwtVerifier;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(value = "/get/{projectid}", method = RequestMethod.GET)
    public String getData(@RequestHeader("Authorization") String token, @PathVariable("projectid") String projectid) throws IOException {
        logger.info("verifying token");
        final DecodedJWT jwt = jwtVerifier.verify(token.replace("Bearer ", ""));
        final Permission[] perms = objectMapper.readValue(((jwt.getClaims()).get("pms")).asString(), Permission[].class);
        if (!Arrays.stream(perms).filter(p -> p.getProjectid().equals(projectid)).findFirst().isPresent()) {
            logger.info("no permission");
            return "[]";
        }
        logger.info("reading file: " + projectid);
        final String content = new String(Files.readAllBytes(Paths.get(filedir + projectid + ".json")));
        logger.info("returning content");
        return content;
    }
}
