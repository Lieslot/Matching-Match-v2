package com.matchingMatch.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Value("${firebase.admin.sdk.path}")
    private String FIREBASE_ADMIN_SDK_PATH;


    @PostConstruct
    public void init() {

        ClassPathResource source = new ClassPathResource(FIREBASE_ADMIN_SDK_PATH);

        try (InputStream is = source.getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(is))
                    .build();
            FirebaseApp.initializeApp(options);
            log.info("FirebaseApp is initialized");
        } catch (RuntimeException | IOException e) {
           log.error(e.getMessage());
        }

    }

}
