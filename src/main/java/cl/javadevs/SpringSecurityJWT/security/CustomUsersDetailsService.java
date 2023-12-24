package cl.javadevs.SpringSecurityJWT.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.javadevs.SpringSecurityJWT.model.Roles;
import cl.javadevs.SpringSecurityJWT.model.Usuarios;
import cl.javadevs.SpringSecurityJWT.repositories.IUsuarioRepository;

@Service
public class CustomUsersDetailsService implements UserDetailsService {

    private IUsuarioRepository iUsuarioRepository;

    @Autowired
    public CustomUsersDetailsService(IUsuarioRepository iUsuarioRepository) {
        this.iUsuarioRepository = iUsuarioRepository;
    }

    //Trae una lista de autoridades
    public Collection<GrantedAuthority> mapToAutorities(List<Roles> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    //Metodo para traer a un usuario consus datos
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuarios = iUsuarioRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        return new User(usuarios.getUsername(), usuarios.getPassword(), mapToAutorities(usuarios.getRoles()));
    }

}
