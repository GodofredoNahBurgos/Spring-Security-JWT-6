package cl.javadevs.SpringSecurityJWT.dtos;

import lombok.Data;

//Regresara informacion del token que se creo
@Data
public class DtoAuthRespuesta {
    private String accessToken;
    private String tokenType = "Bearer ";
    
    public DtoAuthRespuesta(String accessToken) {
        this.accessToken = accessToken;
    }
    
}
