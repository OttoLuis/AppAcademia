package com.otto.fitIA.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercicio_db")
public class Exercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do exercício não pode ficar vazio")
    private String nomeExercicio;

    @NotBlank(message = "O grupo muscular não pode ficar vazio")
    private String grupoMuscular;

    @NotBlank(message = "A descrição do exercício não pode ficar vazia")
    private String descricao;

    private String equipamento;


    private String nivelDificuldade;

    public Exercicio(){

    }

    public Exercicio(Long id, String nomeExercicio, String grupoMuscular, String descricao, String equipamento, String nivelDificuldade, List<PlanoExercicio> planoExercicios) {
        this.id = id;
        this.nomeExercicio = nomeExercicio;
        this.grupoMuscular = grupoMuscular;
        this.descricao = descricao;
        this.equipamento = equipamento;
        this.nivelDificuldade = nivelDificuldade;
        this.planoExercicios = planoExercicios;
    }

    @OneToMany(mappedBy = "exercicio")
    @JsonIgnore
    private List<PlanoExercicio> planoExercicios = new ArrayList<>();


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

    public List<PlanoExercicio> getPlanoExercicios() {
        return planoExercicios;
    }

    public void setPlanoExercicios(List<PlanoExercicio> planoExercicios) {
        this.planoExercicios = planoExercicios;
    }
}
