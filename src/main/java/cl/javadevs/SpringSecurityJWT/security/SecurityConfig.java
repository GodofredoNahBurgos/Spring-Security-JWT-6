package cl.javadevs.SpringSecurityJWT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    //Bean para verificar informacion de usuarios que se logueen en nuestra api
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }

    //Encriptar todas nuestras contraseñas
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Inconrporara el filtro de seguridad JSON Web Token
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    //Establece una cadena de filtros de segridad en nuestra aplicacion aca determinaresmos permisos segun roles
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
        .csrf().disable()
        .exceptionHandling()//Permitirmos manejo de exepciones
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .and()
        .sessionManagement()//Gestion de sesiones
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests() //Petion http autorizada
        .requestMatchers("/api/auth/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/celular/crear").hasAuthority("ADMIN")
        .requestMatchers(HttpMethod.GET,"/api/celular/listar").hasAnyAuthority("ADMIN" , "USER")
        .requestMatchers(HttpMethod.GET,"/api/celular/listarId/**").hasAnyAuthority("ADMIN" , "USER")
        .requestMatchers(HttpMethod.DELETE,"/api/celular/eliminar/**").hasAuthority("ADMIN")
        .requestMatchers(HttpMethod.PUT, "/api/celular/actualizar").hasAuthority("ADMIN")
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic();

        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
        
    }
}