package com.otto.fitIA.models;

import jakarta.persistence.*;


@Entity
@Table(name = "planoExercicio_db")
public class PlanoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diaTreino;
    private Integer ordem;
    private Integer series;
    private String repeticoes;
    private Integer segundosDeDescanso;

    public  PlanoExercicio(){

    }

    public PlanoExercicio(Long id, String diaTreino, Integer ordem, Integer series, String repeticoes, Integer segundosDeDescanso, Exercicio exercicio, PlanoTreino planoTreino) {
        this.id = id;
        this.diaTreino = diaTreino;
        this.ordem = ordem;
        this.series = series;
        this.repeticoes = repeticoes;
        this.segundosDeDescanso = segundosDeDescanso;
        this.exercicio = exercicio;
        this.planoTreino = planoTreino;
    }

    @ManyToOne
    @JoinColumn(name = "exercicio_id", nullable = false)
    private Exercicio exercicio;

    @ManyToOne
    @JoinColumn(name = "planoTreino_id", nullable = false)
    private PlanoTreino planoTreino;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiaTreino() {
        return diaTreino;
    }

    public void setDiaTreino(String diaTreino) {
        this.diaTreino = diaTreino;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public String getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(String repeticoes) {
        this.repeticoes = repeticoes;
    }

    public Integer getSegundosDeDescanso() {
        return segundosDeDescanso;
    }

    public void setSegundosDeDescanso(Integer segundosDeDescanso) {
        this.segundosDeDescanso = segundosDeDescanso;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public PlanoTreino getPlanoTreino() {
        return planoTreino;
    }

    public void setPlanoTreino(PlanoTreino planoTreino) {
        this.planoTreino = planoTreino;
    }
}
