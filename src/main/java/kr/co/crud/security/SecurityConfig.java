package kr.co.crud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/* SecurityConfig.java -> Security Filter Chain
 * (1) HTTP 요청에 대한 보안 작업
 * - UsernamePasswordAuthenticationFilter : 사용자가 입력한 인증 정보로 Authentication 객체 생성
 * - http.formLogin() 등 메서드를 통해 설정
 * (2) 사용자 인증(로그인 설정), 인가(접근 권한 설정), 로그아웃 설정
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        /* 인가(접근권한) 설정 */
        http.authorizeHttpRequests().antMatchers("/", "/user/login", "/user/register").permitAll();      // 경로에 대해서 모든 사용자에게 접근을 허용한다는 의미
        http.authorizeHttpRequests().antMatchers("/board/**").hasAnyRole("1", "2");

        /* 사이트 위변조 요청 방지
         * CRSF(Cross-Site Request Forgery) 공격 방지 기능을 비활성화
         * disable -> CRSF 토큰을 사용하지 않도록 설정
         * */
        //http.csrf().disable();

        /* 로그인 설정 */
        http.formLogin()
                .loginPage("/user/login")                                       // 로그인 페이지 경로 설정 (해당 경로를 통해 로그인 진행)
                .defaultSuccessUrl("/")                                         // 로그인 성공 시 이동 경로
                .failureUrl("/user/login?success=100")       // 로그인 실패 시 이동 경로
                .usernameParameter("uid")                                       // 로그인 폼에서 사용자 아이디를 입력받는 Input 필드 이름 지정 (name="uid")
                .passwordParameter("pass");                                     // 로그인 폼에서 사용자 비밀번호를 입력받는 Input 필드 이름 지정 (name="pass")

        /* 로그아웃 설정 */
        http.logout()
                .invalidateHttpSession(true)                                                // 로그아웃 시 세션 무효화
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))          // 로그아웃 요청 URL (POST 요청 URL)
                .logoutSuccessUrl("/user/login?success=200");                               // 로그아웃 성공 시 이동 경로

        /* 로그인 시 세션 유지 */
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        //return http.build(); // build: SecurityConfigurerAdapter의 메서드 중 하나로, HttpSecurity 객체를 반환

    }

    /* 비밀번호 암호화
     * BCrypt 해시 함수를 사용하여 비밀번호를 암호화
     *  */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
