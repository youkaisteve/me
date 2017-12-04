package cn.youkai.core.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @author youkai
 * @date 2017/12/4.
 */
public class SecurityConfig {
    @Value("${config.oauth2.key-store}")
    private String keyStore;

    @Value("${config.oauth2.secret-key}")
    private String secretKey;

    @Value("${config.oauth2.alias}")
    private String alias;

    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keyStore), secretKey.toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));
        return converter;
    }
}
