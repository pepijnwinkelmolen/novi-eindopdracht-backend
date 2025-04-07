package com.demo.novieindopdracht.config;

import com.demo.novieindopdracht.repositories.UserRepository;
import com.demo.novieindopdracht.security.JwtRequestFilter;
import com.demo.novieindopdracht.security.JwtService;
import com.demo.novieindopdracht.security.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService service, UserRepository userRepos) {
        this.jwtService = service;
        this.userRepository = userRepos;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService udService, PasswordEncoder passwordEncoder) {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(udService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST,"/login").permitAll()
                    .requestMatchers(HttpMethod.GET,"/users/login").permitAll()
                    .requestMatchers(HttpMethod.POST,"/users/create").permitAll()
                    .requestMatchers(HttpMethod.PUT,"/users/update/password").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.PUT,"/users/update/username").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.GET, "/profile").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.DELETE, "/users/delete/{id}").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.GET,"/categories").permitAll()
                    .requestMatchers(HttpMethod.GET,"/advertisements").permitAll()
                    .requestMatchers(HttpMethod.GET,"/advertisements/category/{category}").permitAll()
                    .requestMatchers(HttpMethod.GET,"/advertisements/{id}").permitAll()
                    .requestMatchers(HttpMethod.DELETE,"/advertisements/{id}").hasAnyRole("ADMIN", "USER")
                    .requestMatchers(HttpMethod.POST,"/advertisements").permitAll()
                    .requestMatchers("/advertisements/search").permitAll()
                    .requestMatchers("/advertisements/filter").permitAll()
                    //.requestMatchers("/admins").hasAuthority("ROLE_ADMIN")
                    .anyRequest().denyAll()
                )
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}