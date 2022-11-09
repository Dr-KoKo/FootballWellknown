package com.a203.sixback.config;

import com.a203.sixback.auth.*;
import com.a203.sixback.db.enums.RoleType;
import com.a203.sixback.db.repo.UserRefreshTokenRepository;
import com.a203.sixback.handler.OAuth2AuthenticationFailureHandler;
import com.a203.sixback.handler.OAuth2AuthenticationSuccessHandler;
import com.a203.sixback.handler.RestAuthenticationEntryPoint;
import com.a203.sixback.handler.TokenAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    /*
     * UserDetailsService 설정
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(tokenAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                .antMatchers("/api/**").hasAnyAuthority(RoleType.USER.getCode())
////                .antMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
//                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*")
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /*
     * auth 매니저 설정
     * */
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
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
                userRefreshTokenRepository,
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

    /*
     * Cors 설정
     * */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        System.out.println("===========================================");
        System.out.println(corsConfig);
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }



















    ///////////////////////////////////////////////////////////////////////////////////////////////////

//    /**
//     * 인증이 필요없는 URI
//     */
//    private static final String[] GET_PUBLIC_URI = {
//    };
//
//    private static final String[] POST_PUBLIC_URI = {
//    };
//
//    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
//    private final AppProperties appProperties;
//    private final JwtProvider jwtProvider;
//    private final CustomOAuth2UserDetailsService userDetailsService;
//    private final UserRefreshTokenRepository refreshTokenRepository;
//
//    private final String frontUrl;
//
//    public SecurityConfig(TokenAccessDeniedHandler tokenAccessDeniedHandler,
//                          AppProperties appProperties,
//                          JwtProvider jwtProvider,
//                          CustomOAuth2UserDetailsService userDetailsService,
//                          UserRefreshTokenRepository userRefreshTokenRepository,
//                          @Value("${frontUrl}") String frontUrl) {
//        this.tokenAccessDeniedHandler = tokenAccessDeniedHandler;
//        this.appProperties = appProperties;
//        this.jwtProvider = jwtProvider;
//        this.userDetailsService = userDetailsService;
//        this.refreshTokenRepository = userRefreshTokenRepository;
//        this.frontUrl = frontUrl;
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return web -> web.ignoring()
//                .antMatchers(HttpMethod.GET, GET_PUBLIC_URI)
//                .antMatchers(HttpMethod.POST, POST_PUBLIC_URI);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http.cors().configurationSource(corsConfigurationSource())
//                .and()
//                .csrf().disable();
//
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin().disable()
//                .httpBasic().disable();
//
//        http.exceptionHandling()
//                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
//                .accessDeniedHandler(tokenAccessDeniedHandler);
//
//        http.oauth2Login()
//                .authorizationEndpoint().baseUri("/oauth2/authorization")
//                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
//            .and()
//                .redirectionEndpoint().baseUri("/*/oauth2/code/*")
//            .and()
//                .userInfoEndpoint().userService(userDetailsService)
//            .and()
//                .successHandler(oAuth2AuthenticationSuccessHandler())
//                .failureHandler(oAuth2AuthenticationFailureHandler());
//        return http.build();
//    }
//
//    /*
//     * auth 매니저 설정
//     * */
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//            throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    /*
//     * security 설정 시, 사용할 인코더 설정
//     * */
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /*
//     * 토큰 필터 설정
//     * */
//    @Bean
//    public TokenAuthenticationFilter tokenAuthenticationFilter() {
//        return new TokenAuthenticationFilter(tokenProvider);
//    }
//
//    /*
//     * 쿠키 기반 인가 Repository
//     * 인가 응답을 연계 하고 검증할 때 사용.
//     * */
//    @Bean
//    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
//        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
//    }
//
//    /*
//     * Oauth 인증 성공 핸들러
//     * */
//    @Bean
//    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
//        return new OAuth2AuthenticationSuccessHandler(
//                tokenProvider,
//                appProperties,
//                refreshTokenRepository,
//                oAuth2AuthorizationRequestBasedOnCookieRepository()
//        );
//    }
//
//    /*
//     * Oauth 인증 실패 핸들러
//     * */
//    @Bean
//    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
//        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
//    }
//
//    private CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        configuration.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}
