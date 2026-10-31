package com.otto.fitIA.service;

import com.otto.fitIA.dto.*;
import com.otto.fitIA.exception.RegraDeNegocioException;
import com.otto.fitIA.ia.GroqClient;
import com.otto.fitIA.models.*;
import com.otto.fitIA.repository.*;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
;

@Service
public class TreinoIAService {

    private final GroqClient groqClient;
    private final PerfilFisicoRepository perfilFisicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ExercicioRepository exercicioRepository;
    private final PlanoTreinoRepository planoTreinoRepository;
    private final PlanoExercicioRepository planoExercicioRepository;
    private final ObjectMapper objectMapper;

    public TreinoIAService(
            GroqClient groqClient,
            PerfilFisicoRepository perfilFisicoRepository,
            UsuarioRepository usuarioRepository,
            ExercicioRepository exercicioRepository,
            PlanoTreinoRepository planoTreinoRepository,
            PlanoExercicioRepository planoExercicioRepository,
            ObjectMapper objectMapper
    ) {
        this.groqClient = groqClient;
        this.perfilFisicoRepository = perfilFisicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.exercicioRepository = exercicioRepository;
        this.planoTreinoRepository = planoTreinoRepository;
        this.planoExercicioRepository = planoExercicioRepository;
        this.objectMapper = objectMapper;
    }

    // Busca o usuário autenticado pelo JWT
    private Usuario obterUsuarioAutenticado() {

        Authentication autenticacao = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (autenticacao == null
                || !autenticacao.isAuthenticated()
                || "anonymousUser".equals(autenticacao.getPrincipal())) {

            throw new RegraDeNegocioException(
                    "Usuário não autenticado."
            );
        }

        return usuarioRepository.findByEmail(autenticacao.getName())
                .orElseThrow(() -> new RegraDeNegocioException(
                        "Usuário autenticado não encontrado."
                ));
    }

    // Busca o perfil físico do usuário
    private PerfilFisico obterPerfilFisico(Usuario usuario) {

        return perfilFisicoRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new RegraDeNegocioException(
                        "Cadastre seu perfil físico antes de gerar um treino."
                ));
    }

    // Valida os dados necessários para personalizar o treino
    private void validarPerfilFisico(PerfilFisico perfil) {

        if (perfil.getObjetivo() == null) {
            throw new RegraDeNegocioException(
                    "Informe seu objetivo físico."
            );
        }

        if (perfil.getExperiencia() == null) {
            throw new RegraDeNegocioException(
                    "Informe seu nível de experiência."
            );
        }

        if (perfil.getFrequenciaSemanal() == null
                || perfil.getFrequenciaSemanal() < 1
                || perfil.getFrequenciaSemanal() > 7) {

            throw new RegraDeNegocioException(
                    "A frequência semanal deve ser entre 1 e 7 dias."
            );
        }

        if (perfil.getTempoDisponivel() == null
                || perfil.getTempoDisponivel() <= 0) {

            throw new RegraDeNegocioException(
                    "Informe o tempo disponível para cada treino."
            );
        }

        if (perfil.getEquipamentoDisponivel() == null
                || perfil.getEquipamentoDisponivel().isBlank()) {

            throw new RegraDeNegocioException(
                    "Informe os equipamentos disponíveis."
            );
        }

        if (perfil.getIdade() == null || perfil.getIdade() <= 0) {
            throw new RegraDeNegocioException(
                    "Informe uma idade válida."
            );
        }

        if (perfil.getPeso() == null || perfil.getPeso() <= 0) {
            throw new RegraDeNegocioException(
                    "Informe um peso válido."
            );
        }

        if (perfil.getAltura() == null || perfil.getAltura() <= 0) {
            throw new RegraDeNegocioException(
                    "Informe uma altura válida."
            );
        }
    }

    // Busca os exercícios cadastrados no banco
    private List<Exercicio> obterCatalogoExercicios() {

        List<Exercicio> exercicios = exercicioRepository.findAll();

        if (exercicios.isEmpty()) {
            throw new RegraDeNegocioException(
                    "Não existem exercícios cadastrados para gerar o treino."
            );
        }

        return exercicios;
    }

    // Transforma o catálogo em texto para enviar à Groq
    private String montarCatalogoExercicios(List<Exercicio> exercicios) {

        return exercicios.stream()
                .map(exercicio ->
                        "ID: " + exercicio.getId()
                                + " | Nome: " + exercicio.getNomeExercicio()
                                + " | Grupo muscular: " + exercicio.getGrupoMuscular()
                                + " | Descrição: " + exercicio.getDescricao()
                                + " | Equipamento: " + exercicio.getEquipamento()
                                + " | Dificuldade: " + exercicio.getNivelDificuldade()
                )
                .collect(Collectors.joining("\n"));
    }

    // Monta o prompt personalizado para a Groq
    private String montarPrompt(
            PerfilFisico perfil,
            List<Exercicio> exercicios
    ) {

        String catalogo = montarCatalogoExercicios(exercicios);

        return """
                Você é um especialista em elaboração de treinos físicos.

                Gere um plano de treino personalizado com base nos dados do usuário.

                DADOS DO USUÁRIO:

                Objetivo: %s
                Experiência: %s
                Frequência semanal: %d dias
                Tempo disponível por treino: %d minutos
                Equipamentos disponíveis: %s
                Idade: %d anos
                Peso: %.1f kg
                Altura: %.2f metros

                CATÁLOGO DE EXERCÍCIOS DISPONÍVEIS:

                %s

                REGRAS OBRIGATÓRIAS:

                1. Utilize exclusivamente exercícios do catálogo fornecido.
                2. Informe o ID exato de cada exercício selecionado.
                3. Respeite o objetivo e o nível de experiência do usuário.
                4. Distribua os exercícios ao longo da frequência semanal informada.
                5. Considere o tempo disponível por treino.
                6. Não selecione equipamentos que o usuário não possui.
                7. Organize os exercícios pela ordem de execução.
                8. Utilize séries, repetições e descanso adequados.
                9. Não invente exercícios nem IDs.
                10. Evite volumes excessivos e priorize uma distribuição equilibrada.
                11. Utilize somente os dias: SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO e DOMINGO.
                12. Retorne pelo menos um exercício para cada dia de treino planejado.
                13. Não inclua comentários ou explicações fora do JSON.

                FORMATO DA RESPOSTA:

                Retorne exclusivamente um JSON válido, sem Markdown,
                sem explicações e sem blocos de código.

                Estrutura obrigatória:

                {
                  "nomeTreino": "Nome do plano",
                  "exercicios": [
                    {
                      "exercicioId": 1,
                      "diaTreino": "SEGUNDA",
                      "ordem": 1,
                      "series": 3,
                      "repeticoes": "10-12",
                      "segundosDeDescanso": 90
                    }
                  ]
                }
                """.formatted(
                perfil.getObjetivo().name(),
                perfil.getExperiencia().name(),
                perfil.getFrequenciaSemanal(),
                perfil.getTempoDisponivel(),
                perfil.getEquipamentoDisponivel(),
                perfil.getIdade(),
                perfil.getPeso(),
                perfil.getAltura(),
                catalogo
        );
    }

    // Interpreta o JSON retornado pela Groq
    private TreinoGeradoIA interpretarResposta(String resposta) {

        try {
            String json = resposta.trim();

            // Remove blocos Markdown caso a IA os retorne
            if (json.startsWith("```")) {
                json = json.replaceFirst("^```(?:json)?\\s*", "");
                json = json.replaceFirst("\\s*```$", "");
            }

            TreinoGeradoIA treino = objectMapper.readValue(
                    json,
                    TreinoGeradoIA.class
            );

            if (treino == null
                    || treino.nomeTreino() == null
                    || treino.nomeTreino().isBlank()
                    || treino.exercicios() == null
                    || treino.exercicios().isEmpty()) {

                throw new RegraDeNegocioException(
                        "A IA retornou um treino incompleto."
                );
            }

            return treino;

        } catch (RegraDeNegocioException e) {
            throw e;

        } catch (Exception e) {
            throw new RegraDeNegocioException(
                    "Não foi possível interpretar o treino gerado pela IA."
            );
        }
    }

    // Valida os exercícios retornados pela IA
    private Map<Long, Exercicio> validarExerciciosGerados(
            TreinoGeradoIA treino,
            List<Exercicio> catalogo
    ) {

        Map<Long, Exercicio> exerciciosDisponiveis = new HashMap<>();

        for (Exercicio exercicio : catalogo) {
            exerciciosDisponiveis.put(exercicio.getId(), exercicio);
        }

        Set<String> diasValidos = Set.of(
                "SEGUNDA",
                "TERCA",
                "QUARTA",
                "QUINTA",
                "SEXTA",
                "SABADO",
                "DOMINGO"
        );

        Set<String> ordensUtilizadas = new java.util.HashSet<>();

        for (ExercicioGeradoIA item : treino.exercicios()) {

            if (item == null) {
                throw new RegraDeNegocioException(
                        "A IA retornou um exercício inválido."
                );
            }

            if (item.exercicioId() == null
                    || !exerciciosDisponiveis.containsKey(item.exercicioId())) {

                throw new RegraDeNegocioException(
                        "A IA selecionou um exercício que não existe no catálogo."
                );
            }

            String dia = item.diaTreino() == null
                    ? ""
                    : item.diaTreino().trim().toUpperCase(Locale.ROOT);

            if (!diasValidos.contains(dia)) {
                throw new RegraDeNegocioException(
                        "A IA retornou um dia de treino inválido."
                );
            }

            if (item.ordem() == null || item.ordem() < 1) {
                throw new RegraDeNegocioException(
                        "A ordem do exercício é inválida."
                );
            }

            String chaveOrdem = dia + ":" + item.ordem();

            if (!ordensUtilizadas.add(chaveOrdem)) {
                throw new RegraDeNegocioException(
                        "A IA retornou exercícios com ordens repetidas no mesmo dia."
                );
            }

            if (item.series() == null
                    || item.series() < 1
                    || item.series() > 10) {

                throw new RegraDeNegocioException(
                        "A quantidade de séries deve estar entre 1 e 10."
                );
            }

            if (item.repeticoes() == null
                    || item.repeticoes().isBlank()
                    || item.repeticoes().length() > 30) {

                throw new RegraDeNegocioException(
                        "As repetições informadas são inválidas."
                );
            }

            if (item.segundosDeDescanso() == null
                    || item.segundosDeDescanso() < 0
                    || item.segundosDeDescanso() > 600) {

                throw new RegraDeNegocioException(
                        "O descanso deve estar entre 0 e 600 segundos."
                );
            }
        }

        return exerciciosDisponiveis;
    }

    @Transactional
    public GerarTreinoIAResponse gerarTreino() {

        // 1. Busca o usuário autenticado
        Usuario usuario = obterUsuarioAutenticado();

        // 2. Busca e valida o perfil físico
        PerfilFisico perfil = obterPerfilFisico(usuario);
        validarPerfilFisico(perfil);

        // 3. Busca o catálogo de exercícios
        List<Exercicio> catalogo = obterCatalogoExercicios();

        // 4. Monta o prompt e envia para a Groq
        String prompt = montarPrompt(perfil, catalogo);

        String respostaGroq = groqClient.enviarMensagem(prompt);

        // 5. Interpreta e valida a resposta
        TreinoGeradoIA treino = interpretarResposta(respostaGroq);

        Map<Long, Exercicio> exerciciosDisponiveis =
                validarExerciciosGerados(treino, catalogo);

        // 6. Cria o plano de treino
        PlanoTreino plano = new PlanoTreino();

        plano.setNomeTreino(treino.nomeTreino());
        plano.setObjetivo(perfil.getObjetivo().name());
        plano.setDataInicio(LocalDate.now());
        plano.setDataFim(LocalDate.now().plusDays(28));
        plano.setUsuario(usuario);

        PlanoTreino planoSalvo = planoTreinoRepository.save(plano);

        // 7. Cria os exercícios vinculados ao plano
        List<PlanoExercicio> exerciciosParaSalvar = new ArrayList<>();

        List<ExercicioTreinoIAResponse> exerciciosResponse = new ArrayList<>();

        for (ExercicioGeradoIA item : treino.exercicios()) {

            Exercicio exercicio = exerciciosDisponiveis.get(item.exercicioId());

            PlanoExercicio planoExercicio = new PlanoExercicio();

            planoExercicio.setDiaTreino(
                    item.diaTreino().trim().toUpperCase(Locale.ROOT)
            );

            planoExercicio.setOrdem(item.ordem());
            planoExercicio.setSeries(item.series());
            planoExercicio.setRepeticoes(item.repeticoes().trim());
            planoExercicio.setSegundosDeDescanso(item.segundosDeDescanso());

            // Relacionamentos JPA
            planoExercicio.setExercicio(exercicio);
            planoExercicio.setPlanoTreino(planoSalvo);

            exerciciosParaSalvar.add(planoExercicio);

            // Monta os dados que serão devolvidos pela API
            exerciciosResponse.add(
                    new ExercicioTreinoIAResponse(
                            exercicio.getId(),
                            exercicio.getNomeExercicio(),
                            exercicio.getGrupoMuscular(),
                            planoExercicio.getDiaTreino(),
                            planoExercicio.getOrdem(),
                            planoExercicio.getSeries(),
                            planoExercicio.getRepeticoes(),
                            planoExercicio.getSegundosDeDescanso()
                    )
            );
        }

        // 8. Salva todos os exercícios no banco
        planoExercicioRepository.saveAll(exerciciosParaSalvar);

        // 9. Retorna o plano e seus exercícios
        PlanoTreinoResponse planoResponse = new PlanoTreinoResponse(
                planoSalvo.getId(),
                planoSalvo.getNomeTreino(),
                planoSalvo.getObjetivo(),
                planoSalvo.getDataInicio(),
                planoSalvo.getDataFim(),
                usuario.getId()
        );

        return new GerarTreinoIAResponse(
                planoResponse,
                exerciciosResponse
        );
    }
}
