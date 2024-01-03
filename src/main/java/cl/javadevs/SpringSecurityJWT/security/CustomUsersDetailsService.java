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

    //Método para traernos una lista de autoridades por medio de una lista de roles
    public Collection<GrantedAuthority> mapToAutorities(List<Roles> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    //Método para traernos un usuario con todos sus datos por medio de sus username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuarios = iUsuarioRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        return new User(usuarios.getUsername(), usuarios.getPassword(), mapToAutorities(usuarios.getRoles()));
    }

}
