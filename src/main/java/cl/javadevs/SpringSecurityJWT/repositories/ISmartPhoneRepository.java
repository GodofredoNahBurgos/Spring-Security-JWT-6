package cl.javadevs.SpringSecurityJWT.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.javadevs.SpringSecurityJWT.model.SmartPhone;

@Repository
public interface ISmartPhoneRepository extends JpaRepository<SmartPhone, Long> {

}
