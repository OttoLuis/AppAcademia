package com.otto.fitIA.controller;

import com.otto.fitIA.dto.UsuarioDto;
import com.otto.fitIA.dto.createDto.CreateUsuario;
import com.otto.fitIA.mapper.UsuarioMapper;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
   private PasswordEncoder passwordEncoder;


    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<UsuarioDto> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDto)
                .toList();
    }
    @PostMapping
    public UsuarioDto criar(@Valid @RequestBody CreateUsuario dto) {

        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return UsuarioMapper.toDto(usuarioSalvo);
    }

}