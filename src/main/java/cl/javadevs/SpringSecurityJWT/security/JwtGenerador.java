package cl.javadevs.SpringSecurityJWT.security;

import java.util.Date;

import javax.naming.AuthenticationException;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JwtGenerador {

    //Metodo para crear el token por medio de autentificacion
    public String generarToken(Authentication authentication){
        String username = authentication.getName();
        Date tiempoActual = new Date();
        Date expiracionToken = new Date(tiempoActual.getTime() + ConstantesSeguridad.JWT_EXPIRATION_TOKEN);
        
        //Linea para generar el token
        String token = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(expiracionToken)
        .signWith(SignatureAlgorithm.HS512, ConstantesSeguridad.JWT_FIRMA)
        .compact();

        return token;

    }

    //Extraer un username de un token
    public String obtenerUsernameDeJwt(String token){
        Claims claims = Jwts.parser()
        .setSigningKey(ConstantesSeguridad.JWT_FIRMA)
        .parseClaimsJws(token)
        .getBody();
        return claims.getSubject();
    }

    //Para validar el token
    public boolean validarToken(String token){
        try {
            Jwts.parser()
            .setSigningKey(ConstantesSeguridad.JWT_FIRMA).parseClaimsJws(token);
            return true;        
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT ah expirado o esta incorrecto");
        }

    }

}
