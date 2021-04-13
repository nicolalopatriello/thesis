package it.nicolalopatriello.thesis.core.configuration;


import it.nicolalopatriello.thesis.common.spring.services.JwtTokenService;
import it.nicolalopatriello.thesis.common.spring.services.JwtTokenServiceImpl;
import it.nicolalopatriello.thesis.common.utils.rsa.PrivateKeyReader;
import it.nicolalopatriello.thesis.common.utils.rsa.PublicKeyReader;
import it.nicolalopatriello.thesis.core.security.KirkBCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

@Configuration
@ComponentScan({"it.nicolalopatriello.thesis.core", "it.nicolalopatriello.thesis.common"})
@EnableAsync
public class AppConfiguration {

    @Value(value = "${privatekey.filename}")
    String privateKeyFilename;

    @Value(value = "${publickey.filename}")
    String publicKeyFilename;

    @Value("${jwt.expiration}")
    private Long expiration;


    @Value("${sarbox.bcrypt.strength:12}")
    private int bcryptStrength;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new KirkBCryptPasswordEncoder(bcryptStrength);
    }

    ClassLoader classLoader = getClass().getClassLoader();

 /*  @Bean
    SarboxDictionaries sarboxDictionaries() throws IOException {
        return new SarboxDictionaries(dictionariesFiles);
    }*/

    @Bean(name = "publicKey")
    PublicKey getPublicKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        File file = new File(publicKeyFilename);
        if (file.isAbsolute())
            return PublicKeyReader.get(publicKeyFilename);
        return PublicKeyReader.get(Objects.requireNonNull(classLoader.getResource(publicKeyFilename)).getPath());
    }

    @Bean(name = "privateKey")
    PrivateKey getPrivateKey() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        File file = new File(publicKeyFilename);
        if (file.isAbsolute())
            return PrivateKeyReader.get(privateKeyFilename);
        return PrivateKeyReader.get(Objects.requireNonNull(classLoader.getResource(privateKeyFilename)).getPath());
    }

    @Bean
    @DependsOn({"publicKey", "privateKey"})
    JwtTokenService jwtTokenService() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        return new JwtTokenServiceImpl(getPublicKey(), getPrivateKey(), expiration);
    }


}
