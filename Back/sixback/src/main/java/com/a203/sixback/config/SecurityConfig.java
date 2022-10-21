package com.a203.sixback.config;

import com.a203.sixback.auth.AppProperties;
import com.a203.sixback.auth.CustomUserDetailsService;
import com.a203.sixback.auth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.a203.sixback.db.repo.RefreshTokenRepository;
import com.a203.sixback.handler.OAuth2AuthenticationFailureHandler;
import com.a203.sixback.handler.OAuth2AuthenticationSuccessHandler;
import com.a203.sixback.handler.RestAuthenticationEntryPoint;
import com.a203.sixback.handler.TokenAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 인증이 필요없는 URI
     */
    private static final String[] GET_PUBLIC_URI = {
    };

    private static final String[] POST_PUBLIC_URI = {
    };

    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    private final String frontUrl;

    public SecurityConfig(TokenAccessDeniedHandler tokenAccessDeniedHandler,
                          AppProperties appProperties,
                          AuthTokenProvider tokenProvider,
                          CustomUserDetailsService userDetailsService,
                          RefreshTokenRepository userRefreshTokenRepository,
                          @Value("${frontUrl}") String frontUrl) {
        this.tokenAccessDeniedHandler = tokenAccessDeniedHandler;
        this.appProperties = appProperties;
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
        this.refreshTokenRepository = userRefreshTokenRepository;
        this.frontUrl = frontUrl;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .antMatchers(HttpMethod.GET, GET_PUBLIC_URI)
                .antMatchers(HttpMethod.POST, POST_PUBLIC_URI);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable();

        http.exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(tokenAccessDeniedHandler);

        http.oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorization")
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
            .and()
                .redirectionEndpoint().baseUri("/*/oauth2/code/*")
            .and()
                .userInfoEndpoint().userService(userDetailsService)
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());
        return http.build();
    }

    /*
     * auth 매니저 설정
     * */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
     * security 설정 시, 사용할 인코더 설정
     * */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * 토큰 필터 설정
     * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    /*
     * 쿠키 기반 인가 Repository
     * 인가 응답을 연계 하고 검증할 때 사용.
     * */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /*
     * Oauth 인증 성공 핸들러
     * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
