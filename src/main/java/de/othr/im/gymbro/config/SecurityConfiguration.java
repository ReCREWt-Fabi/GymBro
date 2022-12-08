package de.othr.im.gymbro.config;

import de.othr.im.gymbro.service.GymBroUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    GymBroUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/unauthenticated/**", "/error/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable().formLogin().loginPage("/unauthenticated/login").defaultSuccessUrl("/home")
                .and().logout().logoutUrl("/unauthenticated/logout").logoutSuccessUrl("/unauthenticated/login?logout").invalidateHttpSession(true).clearAuthentication(true)
                .permitAll();
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }

}
