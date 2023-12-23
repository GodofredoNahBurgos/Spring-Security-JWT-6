package cl.javadevs.SpringSecurityJWT.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.javadevs.SpringSecurityJWT.model.Roles;

@Repository
public interface IRolesReposiroty extends JpaRepository<Roles, Long>{

    Optional<Roles> findByName(String name);

}
