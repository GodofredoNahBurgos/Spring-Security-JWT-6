package cl.javadevs.SpringSecurityJWT.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.javadevs.SpringSecurityJWT.model.Usuarios;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuarios, Long>{

    Optional<Usuarios> findByUsername(String username);

    Boolean exexistsByUsername(String username);

}
