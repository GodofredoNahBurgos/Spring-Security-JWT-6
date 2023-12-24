package cl.javadevs.SpringSecurityJWT.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.javadevs.SpringSecurityJWT.model.SmartPhone;
import cl.javadevs.SpringSecurityJWT.services.SmartPhoneService;

@RestController
@RequestMapping("/api/celular/")
public class RestControllerSmartPhone {

    private SmartPhoneService phoneService;

    @Autowired
    public RestControllerSmartPhone(SmartPhoneService phoneService) {
        this.phoneService = phoneService;
    }

    //Petición para crear un  celular
    @PostMapping(value = "crear", headers = "Accept=application/json")
    public void crearCelular(@RequestBody SmartPhone smartPhone) {
        phoneService.crear(smartPhone);
    }

    //Petición para obtener todos los celulares en la BD
    @GetMapping(value = "listar", headers = "Accept=application/json")
    public List<SmartPhone> listarCelulares() {
        return phoneService.readAll();
    }

    //Petición para obtener celular mediante "ID"
    @GetMapping(value = "listarId/{id}", headers = "Accept=application/json")
    public Optional<SmartPhone> obtenerCelularPorId(@PathVariable Long id) {
        return phoneService.readOne(id);
    }

    //Petición para actualizar un celular
    @PutMapping(value = "actualizar", headers = "Accept=application/json")
    public void actualizarCelular(@RequestBody SmartPhone smartPhone) {
        phoneService.update(smartPhone);
    }

    //Petición para eliminar un celular por "Id"
    @DeleteMapping(value = "eliminar/{id}", headers = "Accept=application/json")
    public void eliminarCelular(@PathVariable Long id) {
        phoneService.delete(id);
    }

}
