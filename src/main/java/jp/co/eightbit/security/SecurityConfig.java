package jp.co.eightbit.security;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .formLogin(login -> login                
                .loginPage("/login").permitAll()	//①
                .defaultSuccessUrl("/")  //②
                .usernameParameter("email")    //③
                .passwordParameter("password")           //④
                .successHandler((request,response,authentication) -> {
                	response.sendRedirect("/");
                })
                )
        .logout(logout -> logout
        		.logoutSuccessUrl("/login?logout") //⑤
        	    .invalidateHttpSession(true))
        .authorizeHttpRequests(authz -> authz
        		.requestMatchers("/css","/js").permitAll()
        		.requestMatchers("/register").permitAll()
        		.anyRequest().authenticated()
        )
        .csrf().disable();
        return http.build();
    }
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}