package com.otto.fitIA.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planoTreino_db")
public class PlanoTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do treino é obrigatório")
    private String nomeTreino;

    @NotBlank(message = "O objetivo do treino é obrigatório")
    private String objetivo;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataInicio;

    @NotNull(message = "A data de término é obrigatória")
    private LocalDate dataFim;

    public PlanoTreino(){

    }

    public PlanoTreino(Long id, String nomeTreino, String objetivo, LocalDate dataInicio, LocalDate dataFim, Usuario usuario, List<PlanoExercicio> planoExercicios) {
        this.id = id;
        this.nomeTreino = nomeTreino;
        this.objetivo = objetivo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.usuario = usuario;
        this.planoExercicios = planoExercicios;
    }

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "planoTreino")
    private List<PlanoExercicio> planoExercicios = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeTreino() {
        return nomeTreino;
    }

    public void setNomeTreino(String nomeTreino) {
        this.nomeTreino = nomeTreino;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<PlanoExercicio> getPlanoExercicios() {
        return planoExercicios;
    }

    public void setPlanoExercicios(List<PlanoExercicio> planoExercicios) {
        this.planoExercicios = planoExercicios;
    }
}
