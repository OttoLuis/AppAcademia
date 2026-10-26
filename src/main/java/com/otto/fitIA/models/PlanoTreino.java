package com.otto.fitIA.models;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planoTreino_db")
public class PlanoTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeTreino;
    private String objetivo;
    private LocalDate dataInicio;
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
