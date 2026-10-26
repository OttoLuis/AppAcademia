package com.otto.fitIA.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "exercicio_db")
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeExercicio;
    private String grupoMuscular;
    private String descricao;
    private String equipamento;
    private String nivelDificuldade;

    public Exercicio(){

    }

    public Exercicio(Long id, String nomeExercicio, String grupoMuscular, String descricao, String equipamento, String nivelDificuldade, PlanoExercicio planoExercicio) {
        this.id = id;
        this.nomeExercicio = nomeExercicio;
        this.grupoMuscular = grupoMuscular;
        this.descricao = descricao;
        this.equipamento = equipamento;
        this.nivelDificuldade = nivelDificuldade;
        this.planoExercicio = planoExercicio;
    }



    @OneToOne(mappedBy = "exercicio")
    @JsonIgnore
    private PlanoExercicio planoExercicio;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeExercicio() {
        return nomeExercicio;
    }

    public void setNomeExercicio(String nomeExercicio) {
        this.nomeExercicio = nomeExercicio;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public String getNivelDificuldade() {
        return nivelDificuldade;
    }

    public void setNivelDificuldade(String nivelDificuldade) {
        this.nivelDificuldade = nivelDificuldade;
    }

    public PlanoExercicio getPlanoExercicio() {
        return planoExercicio;
    }

    public void setPlanoExercicio(PlanoExercicio planoExercicio) {
        this.planoExercicio = planoExercicio;
    }
}
