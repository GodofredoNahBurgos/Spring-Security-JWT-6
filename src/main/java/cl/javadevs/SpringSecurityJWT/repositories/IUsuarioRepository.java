package cl.javadevs.SpringSecurityJWT.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.javadevs.SpringSecurityJWT.model.Usuarios;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuarios, Long>{

    //Método para poder buscar un usuario mediante su nombre
    Optional<Usuarios> findByUsername(String username);

    //Método para poder verificar si un usuario existe en nuestra base de datos
    Boolean existsByUsername(String username);

}
