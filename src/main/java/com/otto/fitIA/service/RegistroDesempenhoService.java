
package com.otto.fitIA.service;

import com.otto.fitIA.dto.request.RegistroDesempenhoRequest;
import com.otto.fitIA.dto.response.RegistroDesempenhoResponse;
import com.otto.fitIA.exception.RecursoNaoEncontradoException;
import com.otto.fitIA.exception.RegraDeNegocioException;
import com.otto.fitIA.models.*;
import com.otto.fitIA.repository.*;
import com.otto.fitIA.repository.PlanoExercicioRepository;
import com.otto.fitIA.repository.RegistroDesempenhoRepository;
import com.otto.fitIA.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistroDesempenhoService {

    private final RegistroDesempenhoRepository registroRepository;
    private final PlanoExercicioRepository planoExercicioRepository;
    private final UsuarioRepository usuarioRepository;

    public RegistroDesempenhoService(
            RegistroDesempenhoRepository registroRepository,
            PlanoExercicioRepository planoExercicioRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.registroRepository = registroRepository;
        this.planoExercicioRepository = planoExercicioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario obterUsuarioAutenticado() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            throw new RegraDeNegocioException("Usuário não autenticado");
        }

        String email = authentication.getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RegraDeNegocioException("Usuário não encontrado"));
    }

    private PlanoExercicio buscarPlanoExercicio(
            Long planoExercicioId,
            Usuario usuario
    ) {
        PlanoExercicio planoExercicio =
                planoExercicioRepository.findById(planoExercicioId)
                        .orElseThrow(() ->
                                new RecursoNaoEncontradoException(
                                        "Exercício do plano", planoExercicioId));

        if (!planoExercicio.getPlanoTreino()
                .getUsuario().getId().equals(usuario.getId())) {
            throw new RegraDeNegocioException(
                    "Você não tem permissão para acessar este exercício");
        }

        return planoExercicio;
    }

    @Transactional
    public RegistroDesempenhoResponse cadastrar(
            RegistroDesempenhoRequest request,
            Long planoExercicioId
    ) {
        Usuario usuario = obterUsuarioAutenticado();

        PlanoExercicio planoExercicio =
                buscarPlanoExercicio(planoExercicioId, usuario);

        RegistroDesempenho registro = new RegistroDesempenho();
        registro.setDataRegistro(request.dataRegistro());
        registro.setCargaUtilizada(request.cargaUtilizada());
        registro.setSeriesRealizadas(request.seriesRealizadas());
        registro.setRepeticoesRealizadas(request.repeticoesRealizadas());
        registro.setObservacao(request.observacao());
        registro.setPlanoExercicio(planoExercicio);

        RegistroDesempenho salvo = registroRepository.save(registro);

        return converterResponse(salvo);
    }

    @Transactional(readOnly = true)
    public List<RegistroDesempenhoResponse> listarPorExercicio(
            Long planoExercicioId
    ) {
        Usuario usuario = obterUsuarioAutenticado();

        buscarPlanoExercicio(planoExercicioId, usuario);

        return registroRepository
                .findByPlanoExercicioIdOrderByDataRegistroAsc(planoExercicioId)
                .stream()
                .map(this::converterResponse)
                .toList();
    }

    private RegistroDesempenhoResponse converterResponse(
            RegistroDesempenho registro
    ) {
        return new RegistroDesempenhoResponse(
                registro.getId(),
                registro.getDataRegistro(),
                registro.getCargaUtilizada(),
                registro.getSeriesRealizadas(),
                registro.getRepeticoesRealizadas(),
                registro.getObservacao(),
                registro.getPlanoExercicio().getId(),
                registro.getPlanoExercicio().getExercicio().getNomeExercicio()
        );
    }
}
