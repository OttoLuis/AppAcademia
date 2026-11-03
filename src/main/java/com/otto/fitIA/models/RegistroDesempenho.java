
package com.otto.fitIA.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "registroDesempenho_db")
public class RegistroDesempenho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data do registro é obrigatória")
    private LocalDate dataRegistro;

    @NotNull(message = "A carga utilizada é obrigatória")
    @PositiveOrZero(message = "A carga não pode ser negativa")
    private BigDecimal cargaUtilizada;

    @NotNull(message = "A quantidade de séries é obrigatória")
    @Positive(message = "As séries devem ser maiores que zero")
    private Integer seriesRealizadas;

    @NotNull(message = "As repetições são obrigatórias")
    @Positive(message = "As repetições devem ser maiores que zero")
    private Integer repeticoesRealizadas;

    @Column(length = 500)
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "plano_exercicio_id", nullable = false)
    @NotNull(message = "O exercício do plano é obrigatório")
    private PlanoExercicio planoExercicio;

    public RegistroDesempenho() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public BigDecimal getCargaUtilizada() {
        return cargaUtilizada;
    }

    public void setCargaUtilizada(BigDecimal cargaUtilizada) {
        this.cargaUtilizada = cargaUtilizada;
    }

    public Integer getSeriesRealizadas() {
        return seriesRealizadas;
    }

    public void setSeriesRealizadas(Integer seriesRealizadas) {
        this.seriesRealizadas = seriesRealizadas;
    }

    public Integer getRepeticoesRealizadas() {
        return repeticoesRealizadas;
    }

    public void setRepeticoesRealizadas(Integer repeticoesRealizadas) {
        this.repeticoesRealizadas = repeticoesRealizadas;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public PlanoExercicio getPlanoExercicio() {
        return planoExercicio;
    }

    public void setPlanoExercicio(PlanoExercicio planoExercicio) {
        this.planoExercicio = planoExercicio;
    }
}
