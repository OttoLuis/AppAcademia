package com.otto.fitIA.models;


import com.otto.fitIA.enums.NivelExperiencia;
import com.otto.fitIA.enums.ObjetivoFisico;
import jakarta.persistence.*;

@Entity
@Table(name = "perfilFisico_db")
public class    PerfilFisico{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ObjetivoFisico objetivo;

    @Enumerated(EnumType.STRING)
    private NivelExperiencia experiencia;

    private Integer frequenciaSemanal;

    private Integer tempoDisponivel;

    private String equipamentoDisponivel;

    private Double peso;

    private Double altura;

    private Integer idade;

    public PerfilFisico(){

    }

    @OneToOne
    @MapsId
    private Usuario usuario;


    public PerfilFisico(Long id, ObjetivoFisico objetivo, NivelExperiencia experiencia, Integer frequenciaSemanal, Integer tempoDisponivel, String equipamentoDisponivel, Double peso, Double altura, Integer idade, Usuario usuario) {
        this.id = id;
        this.objetivo = objetivo;
        this.experiencia = experiencia;
        this.frequenciaSemanal = frequenciaSemanal;
        this.tempoDisponivel = tempoDisponivel;
        this.equipamentoDisponivel = equipamentoDisponivel;
        this.peso = peso;
        this.altura = altura;
        this.idade = idade;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public ObjetivoFisico getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(ObjetivoFisico objetivo) {
        this.objetivo = objetivo;
    }

    public NivelExperiencia getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(NivelExperiencia experiencia) {
        this.experiencia = experiencia;
    }

    public Integer getFrequenciaSemanal() {
        return frequenciaSemanal;
    }

    public void setFrequenciaSemanal(Integer frequenciaSemanal) {
        this.frequenciaSemanal = frequenciaSemanal;
    }

    public Integer getTempoDisponivel() {
        return tempoDisponivel;
    }

    public void setTempoDisponivel(Integer tempoDisponivel) {
        this.tempoDisponivel = tempoDisponivel;
    }

    public String getEquipamentoDisponivel() {
        return equipamentoDisponivel;
    }

    public void setEquipamentoDisponivel(String equipamentoDisponivel) {
        this.equipamentoDisponivel = equipamentoDisponivel;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
