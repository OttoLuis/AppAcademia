package com.otto.fitIA.service;



import com.otto.fitIA.models.Meta;
import com.otto.fitIA.models.PlanoTreino;
import com.otto.fitIA.models.Usuario;
import com.otto.fitIA.repository.PlanoTreinoRepository;
import com.otto.fitIA.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlanoTreinoService {

    private final UsuarioRepository usuarioRepository;
    private final PlanoTreinoRepository planoTreinoRepository;

    public PlanoTreinoService(UsuarioRepository usuarioRepository, PlanoTreinoRepository planoTreinoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.planoTreinoRepository = planoTreinoRepository;
    }

    public PlanoTreino cadastrar(PlanoTreino planoTreino) {
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

        planoTreino.setUsuario(usuario);
        return planoTreinoRepository.save(planoTreino);

    }
    public List<PlanoTreino> listarTodos() {
        return planoTreinoRepository.findAll();
    }

    public PlanoTreino buscarPorId(Long id) {
        return planoTreinoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Plano Treino com esse id não encontrado"
                ));
    }
    public PlanoTreino atualizar(Long id, PlanoTreino dados) {
        PlanoTreino planoTreino= buscarPorId(id);

        planoTreino.setNomeTreino(dados.getNomeTreino());
        planoTreino.setObjetivo(dados.getObjetivo());
        planoTreino.setDataInicio(dados.getDataInicio());
        planoTreino.setDataFim(dados.getDataFim());


        return planoTreinoRepository.save(planoTreino);
    }
    public void excluir(Long id) {
        PlanoTreino planoTreino = buscarPorId(id);
        planoTreinoRepository.delete(planoTreino);
    }
}
