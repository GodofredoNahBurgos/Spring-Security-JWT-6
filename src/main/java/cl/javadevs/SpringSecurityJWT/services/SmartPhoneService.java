package cl.javadevs.SpringSecurityJWT.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.javadevs.SpringSecurityJWT.model.SmartPhone;
import cl.javadevs.SpringSecurityJWT.repositories.ISmartPhoneRepository;

@Service
public class SmartPhoneService {

    private ISmartPhoneRepository iSmartPhoneRepository;

    @Autowired
    public SmartPhoneService(ISmartPhoneRepository iSmartPhoneRepository) {
        this.iSmartPhoneRepository = iSmartPhoneRepository;
    }

    //Creamos un celular
    public void crear(SmartPhone smartPhone) {
        iSmartPhoneRepository.save(smartPhone);
    }

    //Obtenemos toda una lista de celulares
    public List<SmartPhone> readAll() {
        return iSmartPhoneRepository.findAll();
    }

    //Obtenemos un celular por su id
    public Optional<SmartPhone> readOne(Long id) {
        return iSmartPhoneRepository.findById(id);
    }

    //Actualizamos un celular
    public void update(SmartPhone smartPhone) {
        iSmartPhoneRepository.save(smartPhone);
    }

    //Eliminamos un celular
    public void delete(Long id) {
        iSmartPhoneRepository.deleteById(id);
    }

}
