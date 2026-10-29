package com.otto.fitIA.controller;

import com.otto.fitIA.dto.UsuarioDto;
import com.otto.fitIA.dto.createDto.CreateUsuario;
import com.otto.fitIA.mapper.UsuarioMapper;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.repository.UsuarioRepository;
import com.otto.fitIA.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(
            UsuarioRepository usuarioRepository,
            UsuarioService usuarioService,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    // GET - listar todos
    @GetMapping
    public List<UsuarioDto> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDto)
                .toList();
    }

    // GET - buscar por ID
    @GetMapping("/{id}")
    public UsuarioDto buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);

        return UsuarioMapper.toDto(usuario);
    }

    // POST - cadastrar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto criar(@Valid @RequestBody CreateUsuario dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return UsuarioMapper.toDto(usuarioSalvo);
    }

    // PUT - atualizar
    @PutMapping("/{id}")
    public UsuarioDto atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CreateUsuario dto
    ) {
        Usuario dadosAtualizados = UsuarioMapper.toEntity(dto);

        if (dadosAtualizados.getSenha() != null
                && !dadosAtualizados.getSenha().isBlank()) {
            dadosAtualizados.setSenha(
                    passwordEncoder.encode(dadosAtualizados.getSenha())
            );
        }

        Usuario usuarioAtualizado = usuarioService.atualizar(
                id,
                dadosAtualizados
        );

        return UsuarioMapper.toDto(usuarioAtualizado);
    }

    // DELETE - excluir
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
    }
}