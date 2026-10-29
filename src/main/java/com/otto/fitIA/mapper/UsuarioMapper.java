
package com.otto.fitIA.mapper;

import com.otto.fitIA.dto.UsuarioDto;
import com.otto.fitIA.dto.createDto.CreateUsuario;
import com.otto.fitIA.models.Usuario;

public class UsuarioMapper {

    public static UsuarioDto toDto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();

        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());

        return dto;
    }

    public static Usuario toEntity(CreateUsuario dto) {
        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        return usuario;
    }
}
