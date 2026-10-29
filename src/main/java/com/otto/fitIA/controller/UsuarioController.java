
package com.otto.fitIA.controller;

import com.otto.fitIA.dto.UsuarioDto;
import com.otto.fitIA.dto.createDto.CreateUsuario;
import com.otto.fitIA.mapper.UsuarioMapper;
import com.otto.fitIA.models.Role;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.repository.RoleRepository;
import com.otto.fitIA.repository.UsuarioRepository;
import com.otto.fitIA.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(
            UsuarioRepository usuarioRepository,
            RoleRepository roleRepository,
            UsuarioService usuarioService,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    // Lista todos os usuários: somente ADMIN
    @GetMapping
    public List<UsuarioDto> findAll(Authentication authentication) {
        usuarioService.exigirAdmin(authentication);

        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDto)
                .toList();
    }

    // Busca por ID: próprio usuário ou ADMIN
    @GetMapping("/{id}")
    public UsuarioDto buscarPorId(
            @PathVariable Long id,
            Authentication authentication
    ) {
        usuarioService.validarAcessoAoUsuario(id, authentication);

        return UsuarioMapper.toDto(usuarioService.buscarPorId(id));
    }

    // Cadastro público
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto criar(@Valid @RequestBody CreateUsuario dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);

        Role roleUsuario = roleRepository.findByAuthority("ROLE_USUARIO")
                .orElseThrow(() -> new IllegalStateException(
                        "A role ROLE_USUARIO não existe no banco de dados."
                ));

        usuario.setRoles(new HashSet<>(List.of(roleUsuario)));
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return UsuarioMapper.toDto(usuarioSalvo);
    }

    // Atualiza a própria conta ou qualquer conta se for ADMIN
    @PutMapping("/{id}")
    public UsuarioDto atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CreateUsuario dto,
            Authentication authentication
    ) {
        usuarioService.validarAcessoAoUsuario(id, authentication);

        Usuario dadosAtualizados = UsuarioMapper.toEntity(dto);

        if (dadosAtualizados.getSenha() != null
                && !dadosAtualizados.getSenha().isBlank()) {
            dadosAtualizados.setSenha(
                    passwordEncoder.encode(dadosAtualizados.getSenha())
            );
        }

        Usuario usuarioAtualizado =
                usuarioService.atualizar(id, dadosAtualizados);

        return UsuarioMapper.toDto(usuarioAtualizado);
    }

    // Exclusão: somente ADMIN
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(
            @PathVariable Long id,
            Authentication authentication
    ) {
        usuarioService.exigirAdmin(authentication);
        usuarioService.excluir(id);
    }
}
