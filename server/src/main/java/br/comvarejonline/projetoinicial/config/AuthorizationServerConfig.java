package br.comvarejonline.projetoinicial.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import br.comvarejonline.projetoinicial.components.JwtTokenEnhancer;

/*
 * Classe responsável por implemnetar a autenticação da aplicação gerando o token JWT
 */
//TODO refatorar para nova versão
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    // CLIENT_ID da aplicação
    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    // CLIENT_SECRET da aplicação
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    // Duração do token JWT (definido para 24h)
    @Value("${jwt.duration}")
    private Integer jwtDuration;

    // Método que adiciona informações ao token
    private JwtTokenEnhancer jwtTokenEnhancer;

    // Algoritmo de criptografia
    private BCryptPasswordEncoder passwordEncoder;

    // Bean que converte o token
    private JwtAccessTokenConverter accessTokenConverter;

    // Bean que retorna o token convertido
    private JwtTokenStore tokenStore;

    // Método que informa o augorítmo de criptografia e o
    // UserDetailsService
    private AuthenticationManager authenticationManager;

    public AuthorizationServerConfig(BCryptPasswordEncoder passwordEncoder,
            JwtAccessTokenConverter accessTokenConverter, JwtTokenStore tokenStore,
            AuthenticationManager authenticationManager, JwtTokenEnhancer jwtTokenEnhancer) {
        this.passwordEncoder = passwordEncoder;
        this.accessTokenConverter = accessTokenConverter;
        this.tokenStore = tokenStore;
        this.authenticationManager = authenticationManager;
        this.jwtTokenEnhancer = jwtTokenEnhancer;
    }

    // Método que permite o acesso se o token está autenticado
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    // Método que define como será feito a autenticação através do CLIENT_ID e
    // CLIENT_SECRET
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId) // CLIENT_ID
                .secret(passwordEncoder.encode(clientSecret)) // CLIENT_SECRET
                .scopes("read", "write") // Escopo de acesso
                .authorizedGrantTypes("password") // GrandType
                .accessTokenValiditySeconds(jwtDuration); // Duração do token
    }

    // Método que define quem fará a autenticação e o formato do toekn
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        // Adicionando informações ao token
        TokenEnhancerChain chain = new TokenEnhancerChain();
        chain.setTokenEnhancers(Arrays.asList(accessTokenConverter, jwtTokenEnhancer));

        endpoints.authenticationManager(authenticationManager) // Informa o rsponsável pela autenticação
                .tokenStore(tokenStore) // Informa o responsável por processar o token
                .accessTokenConverter(accessTokenConverter) // Informa que irá converter o token
                .tokenEnhancer(chain);
    }

}
