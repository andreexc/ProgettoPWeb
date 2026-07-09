package Web.Configs;

import org.springframework.beans.factory.parsing.PassThroughSourceExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Password Encoder Configuration
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // User management with JDBC
    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Setting custom queries for SpringSecurity
        userDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM users WHERE username = ?");
        userDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT username, authority FROM authorities WHERE username = ?");

        return userDetailsManager;
    }

    // Filters, routes and roles configuration
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Public Routes
                .requestMatchers("/", "/index", "/login", "/signup", "/logout", "/registration-success").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll() // Risorse statiche

                // Proteced Routes
                // Spring Security search the role and in its logics appends "ROLE_"
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/dashboard/basic/**").hasRole("USER_BASIC")
                .requestMatchers("/dashboard/pro/**").hasRole("USER_PRO")
                .requestMatchers("/dashboard/prova/**").hasRole("USER_PROVA")

                // NoTrust approach, any other page requires authentication
                .anyRequest().authenticated()
            )
            // Login Form configuration
            .formLogin(form -> form
                .loginPage("/login")                                            // custom login page
                .loginProcessingUrl("/login")                                     // POST login request
                .defaultSuccessUrl("/dashboard", true)   // Role base dispatch after login
                .failureHandler(customAuthenticationFailureHandler())             // Custom error handling
                .permitAll()
            )
            // Logout page configuration
            .logout(logout -> logout
                .logoutSuccessUrl("/logout-success")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    // Custom authentication error handler
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            String errorMessage = "Username o password errati. Riprova.";
            request.getSession().setAttribute("securityErrorMessage", errorMessage); // session is readable by the controller
            // Login redirect with error set
            response.sendRedirect("/login?error=true");
        };
    }
}