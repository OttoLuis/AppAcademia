
package com.otto.fitIA.service;

import com.otto.fitIA.models.PerfilFisico;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.repository.PerfilFisicoRepository;
import com.otto.fitIA.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PerfilFisicoService {

    private final PerfilFisicoRepository repository;
    private final UsuarioRepository usuarioRepository;

    public PerfilFisicoService(
            PerfilFisicoRepository repository,
            UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public PerfilFisico cadastrar(PerfilFisico perfil) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuário não autenticado"
            );
        }

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        if (usuario.getPerfilFisico() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este usuário já possui um perfil físico"
            );
        }

        perfil.setUsuario(usuario);
        usuario.setPerfilFisico(perfil);

        return repository.save(perfil);
    }

    public List<PerfilFisico> listarTodos() {
        return repository.findAll();
    }

    public PerfilFisico buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Perfil físico com esse id não encontrado"
                ));
    }

    public PerfilFisico atualizar(Long id, PerfilFisico dados) {
        PerfilFisico perfil = buscarPorId(id);

        perfil.setPeso(dados.getPeso());
        perfil.setAltura(dados.getAltura());
        perfil.setIdade(dados.getIdade());
        perfil.setObjetivo(dados.getObjetivo());
        perfil.setExperiencia(dados.getExperiencia());
        perfil.setFrequenciaSemanal(dados.getFrequenciaSemanal());
        perfil.setTempoDisponivel(dados.getTempoDisponivel());
        perfil.setEquipamentoDisponivel(dados.getEquipamentoDisponivel());

        return repository.save(perfil);
    }



    public void excluir(Long id) {
        PerfilFisico perfil = buscarPorId(id);
        repository.delete(perfil);
    }
}
