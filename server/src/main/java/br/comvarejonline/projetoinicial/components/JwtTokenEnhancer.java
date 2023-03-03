package br.comvarejonline.projetoinicial.components;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import br.comvarejonline.projetoinicial.entities.User;
import br.comvarejonline.projetoinicial.repositories.UserRepository;

// Classe que adiciona informações ao token
//TODO refatorar para nova versão
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    private UserRepository userRepository;

    public JwtTokenEnhancer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Sobrescre o método que captura o token e adiciona as informações
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        // Busca o usuário por email
        User user = userRepository.findByEmail(authentication.getName());

        Map<String, Object> map = new HashMap<>();
        map.put("userName", user.getName()); // Adiciona o nome do usuário ao token
        map.put("userId", user.getId()); // Adiciona o id do usuário ao token

        // Acessa o token e adiciona as informações
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        token.setAdditionalInformation(map);

        // Retorna o token com as informações adicionais
        return accessToken;

    }

}
