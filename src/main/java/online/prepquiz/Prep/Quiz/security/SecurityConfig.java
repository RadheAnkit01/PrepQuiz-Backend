package online.prepquiz.Prep.Quiz.security;

import lombok.AllArgsConstructor;
import online.prepquiz.Prep.Quiz.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                // Disable CSRF because this is a stateless REST API using JWT
                .csrf(AbstractHttpConfigurer::disable)
                // Enable CORS so Flutter Web can communicate with the backend
                .cors(cors -> {})
                // No HTTP Session will be created
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Authorization rules
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/**").hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
    /*
     |--------------------------------------------------------------------------
     | Allowed Origins
     |--------------------------------------------------------------------------
     |
     | These are the frontend applications allowed to access this backend.
     |
     | Flutter Web -> localhost
     | Android Emulator -> Not required (native apps ignore CORS)
     | iOS Simulator -> Not required
     | Physical Device -> Not required
     |
     | During development we allow every localhost port.
     |
     */

        configuration.setAllowedOriginPatterns(List.of(
                // Flutter Web
                "http://localhost:*",
                // Local network (change 192.168.x.x to your PC IP if needed)
                "http://192.168.*:*"
        ));
    /*
     |--------------------------------------------------------------------------
     | Allowed HTTP Methods
     |--------------------------------------------------------------------------
     */
        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS"
        ));
    /*
     |--------------------------------------------------------------------------
     | Allowed Headers
     |--------------------------------------------------------------------------
     */
        configuration.setAllowedHeaders(List.of("*"));
    /*
     |--------------------------------------------------------------------------
     | Exposed Headers
     |--------------------------------------------------------------------------
     | Useful if you later return JWT tokens or custom headers.
     */
        configuration.setExposedHeaders(List.of("Authorization"));
    /*
     |--------------------------------------------------------------------------
     | Credentials
     |--------------------------------------------------------------------------
     | Allow cookies or Authorization headers.
     */
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
