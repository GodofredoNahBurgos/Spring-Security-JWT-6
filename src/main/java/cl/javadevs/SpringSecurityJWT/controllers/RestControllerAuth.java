package cl.javadevs.SpringSecurityJWT.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.javadevs.SpringSecurityJWT.dtos.DtoAuthRespuesta;
import cl.javadevs.SpringSecurityJWT.dtos.DtoLogin;
import cl.javadevs.SpringSecurityJWT.dtos.DtoRegistro;
import cl.javadevs.SpringSecurityJWT.model.Roles;
import cl.javadevs.SpringSecurityJWT.model.Usuarios;
import cl.javadevs.SpringSecurityJWT.repositories.IRolesReposiroty;
import cl.javadevs.SpringSecurityJWT.repositories.IUsuarioRepository;
import cl.javadevs.SpringSecurityJWT.security.JwtGenerador;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth/*")
public class RestControllerAuth {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRolesReposiroty iRolesReposiroty;
    private IUsuarioRepository iUsuarioRepository;
    private JwtGenerador jwtGenerador;

    @Autowired
    public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
            IRolesReposiroty iRolesReposiroty, IUsuarioRepository iUsuarioRepository, JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.iRolesReposiroty = iRolesReposiroty;
        this.iUsuarioRepository = iUsuarioRepository;
        this.jwtGenerador = jwtGenerador;
    }

    //Metodo para registrar usuario con rol User
    @PostMapping("register")
    public ResponseEntity<String> registrar(@RequestBody DtoRegistro dtoRegistro){
        if (iUsuarioRepository.existsByUsername(dtoRegistro.getUsername())) {
            return new ResponseEntity<>("El usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setUsername(dtoRegistro.getUsername());
        usuarios.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        Roles roles = iRolesReposiroty.findByName("USER").get();
        usuarios.setRoles(Collections.singletonList(roles));
        iUsuarioRepository.save(usuarios);
        return new ResponseEntity<>("Registro de Usuario Exitoso", HttpStatus.OK);
    }

    //Metodo para registrar usuario con rol Admin
    @PostMapping("registerAdm")
    public ResponseEntity<String> registrarAdmin(@RequestBody DtoRegistro dtoRegistro){
        if (iUsuarioRepository.existsByUsername(dtoRegistro.getUsername())) {
            return new ResponseEntity<>("El usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setUsername(dtoRegistro.getUsername());
        usuarios.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        Roles roles = iRolesReposiroty.findByName("ADMIN").get();
        usuarios.setRoles(Collections.singletonList(roles));
        iUsuarioRepository.save(usuarios);
        return new ResponseEntity<>("Registro de Admin Exitoso", HttpStatus.OK);
    }

    //Metodo para logear un usuario y obtener un token
    @PostMapping("/login")
    public ResponseEntity<DtoAuthRespuesta> login(@RequestBody DtoLogin dtoLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dtoLogin.getUsername(), dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerador.generarToken(authentication);
        return new ResponseEntity<>(new DtoAuthRespuesta(token), HttpStatus.OK);
    }
    
}
