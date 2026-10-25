package com.otto.fitIA.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "usuario_db")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario")
    private List<Meta> metas = new ArrayList<>();


    @OneToMany(mappedBy = "usuario")
    private List<PlanoTreino> planoTreinos = new ArrayList<>();

    public Usuario(){

    }

    public Usuario(Long id, String nome, String email, String senha, PerfilFisico perfilFisico) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfilFisico = perfilFisico;
    }
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private PerfilFisico perfilFisico;

    @ManyToMany
    @JoinTable(name = "usuario_role_tb",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilFisico getPerfilFisico() {
        return perfilFisico;
    }

    public List<Meta> getMetas() {
        return metas;
    }

    public void setMetas(List<Meta> metas) {
        this.metas = metas;
    }

    public List<PlanoTreino> getPlanoTreinos() {
        return planoTreinos;
    }

    public void setPlanoTreinos(List<PlanoTreino> planoTreinos) {
        this.planoTreinos = planoTreinos;
    }

    public void setPerfilFisico(PerfilFisico perfilFisico) {
        this.perfilFisico = perfilFisico;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    public void addRole(Role role){
        roles.add(role);

    }
    public boolean hasRole(String roleNome) {
        for (Role role : roles) {
            if (role.getAuthority().equals(roleNome)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }


}









