
package com.otto.fitIA.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "planoExercicio_db")
public class PlanoExercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O dia do treino é obrigatório")
    private String diaTreino;

    @NotNull(message = "A ordem do exercício é obrigatória")
    @Positive(message = "A ordem deve ser maior que zero")
    private Integer ordem;

    @NotNull(message = "A quantidade de séries é obrigatória")
    @Positive(message = "A quantidade de séries deve ser maior que zero")
    private Integer series;

    @NotBlank(message = "As repetições são obrigatórias")
    @Size(max = 30, message = "O campo repetições deve ter no máximo 30 caracteres")
    private String repeticoes;

    @NotNull(message = "O tempo de descanso é obrigatório")
    @PositiveOrZero(message = "O descanso não pode ser negativo")
    private Integer segundosDeDescanso;

    @ManyToOne
    @JoinColumn(name = "exercicio_id", nullable = false)
    @NotNull(message = "O exercício é obrigatório")
    private Exercicio exercicio;

    @ManyToOne
    @JoinColumn(name = "planoTreino_id", nullable = false)
    @NotNull(message = "O plano de treino é obrigatório")
    private PlanoTreino planoTreino;

    public PlanoExercicio() {
    }

    public PlanoExercicio(
            Long id,
            String diaTreino,
            Integer ordem,
            Integer series,
            String repeticoes,
            Integer segundosDeDescanso,
            Exercicio exercicio,
            PlanoTreino planoTreino
    ) {
        this.id = id;
        this.diaTreino = diaTreino;
        this.ordem = ordem;
        this.series = series;
        this.repeticoes = repeticoes;
        this.segundosDeDescanso = segundosDeDescanso;
        this.exercicio = exercicio;
        this.planoTreino = planoTreino;
    }

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
