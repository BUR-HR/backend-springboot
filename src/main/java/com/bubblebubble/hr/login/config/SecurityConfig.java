package com.bubblebubble.hr.login.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.bubblebubble.hr.login.jwt.JwtAccessDeniedHandler;
import com.bubblebubble.hr.login.jwt.JwtAuthenticationEntryPoint;
import com.bubblebubble.hr.login.jwt.TokenProvider;

//  Spring Security를 설정하는 SecurityConfig 클래스입니다. 이 클래스에서는 보안과 관련된 다양한 설정을 수행
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    /* 1. 암호화 처리를 위한 PasswordEncoder를 빈으로 설정 */
    // BCryptPasswordEncoder를 빈으로 등록하여 암호화된 비밀번호를 처리
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* 2. Security 설정을 무시할 정적 리소스 등록 */
    // 정적 리소스 (CSS, JS, 이미지 등)에 대한 보안 설정을 무시(해당 리소스들은 인증이나 인가가 필요하지 않기 때문)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/images/**",
                "/lib/**", "/fileimgs/**");
    }

    /* 3. HTTP요청에 대한 권한별 설정(기존 세션 인증 -> 토큰 인증으로 변경함 :> 추가적으로 코드가 더 생김) */
    // HTTP 요청에 대한 권한과 접근 제어를 설정 , 특정 URL 패턴에 대해 어떤 권한을 가진 사용자만 접근을 허용하도록 지정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .exceptionHandling()
                /* 기본 시큐리티 설정에서 JWT 토큰과 관련된 유효성과 권한 체크용 설정*/
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)//  필요한 권한 없이 접근(403)
                .accessDeniedHandler(jwtAccessDeniedHandler) // 유효한 자격 증명 없을 시(401)
                .and()
                // 권한
                .authorizeRequests()
                .antMatchers("/v3/**","/swagger*/**").permitAll()
                    // 인사카드 등록 페이지 권한(관리자or인사팀장만 접근 및 등록 가능)
//                    .antMatchers("/api/employees/register").hasAnyRole("ROLE_ADMIN", "ROLE_HR_LEADER")
//                .antMatchers("/api/file/**").hasAnyRole("ADMIN", "HR_LEADER")
                .antMatchers("api/file/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()   // cors를 위해 preflight 요청 처리용 option요청 허용
//                    .antMatchers("/api/file/register").hasAnyRole("ADMIN", "HR_LEADER")
                    .antMatchers("/auth/login").permitAll() // 로그인 페이지 모든 사용자 접근 허용
                    .antMatchers("/api/v1/**").permitAll() // 사원조회 페이지
                    .anyRequest().authenticated() // 모든 요청에 대해 인증 필요(ex.로그인한 사용자만 접근)

                .and()

                /* 세션 인증 방식을 쓰지 않겠다는 설정 */
                // JWT 토큰 방식을 사용하므로 세션을 사용하지 않도록 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                /* jwt토큰 방식을 쓰겠다는 설정*/
                .apply(new JwtSecurityConfig(tokenProvider));
        return http.build();
    }


    /* 4. CORS(Cross-origin-resource-sharing) 설정용 Bean */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:3001")); // 어느 도메인에서 온 요청을 허용할 것인지를 지정
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE")); // 어떤 HTTP 메서드를 사용한 요청을 허용할 것인지를 지정
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Content-type" // 어떤 HTTP 헤더를 사용한 요청을 허용할 것인지를 지정
                , "Access-Control-Allow-Headers", "Authorization", "X-Requested-With"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    // CORS는 Cross-Origin Resource Sharing의 약자로, 교차 출처 리소스 공유를 의미
    //  보안 상의 이유로 동일한 출처에서 온 리소스만 접근할 수 있도록 제한되는데, 이로 인해 다른 도메인에서 온 리소스에 접근하는 것이 제한됩니다.
    //  하지만 웹 애플리케이션에서는 다른 도메인으로의 리소스 요청이 필요한 경우가 많습니다. 이때 CORS 정책을 사용하여 도메인 간 리소스 공유를 허용할 수 있습니다.
    // CORS 정책을 설정하면 웹 애플리케이션은 다른 도메인의 리소스에 접근할 수 있는 권한을 부여받게 됩니다.
}