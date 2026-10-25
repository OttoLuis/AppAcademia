package com.otto.fitIA.service;


import com.otto.fitIA.models.Role;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.projection.UserDetailsProjection;
import com.otto.fitIA.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {


    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> resultado = usuarioRepository.searchUserAndRolesByEmail(username);

        if (resultado.size() == 0){ // se a lista(de usuario) estiver vazia cai no exception
            throw new UsernameNotFoundException("Usuario näo encontrado");
        }
        Usuario usuario = new Usuario(); //cria um novo usuario(vazio)
        usuario.setEmail(username); //coloca o email encontrado na consulta
        usuario.setSenha(resultado.get(0).getPassword()); // coloca a senha e pega o primeiro resultado da lista
        for (UserDetailsProjection projection : resultado ){   //percorre os resultados
            usuario.addRole(new Role(projection.getRoleId(), projection.getAuthority())); //cria a role, e adiciona a role ao usuario criado
        }
        return usuario; //retorna o usuario
    }
}
