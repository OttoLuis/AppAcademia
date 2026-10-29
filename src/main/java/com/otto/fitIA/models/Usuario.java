
package com.otto.fitIA.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "usuario_db")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Informe um e-mail válido")
    @Size(max = 254, message = "O e-mail deve ter no máximo 254 caracteres")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
    @Column(nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Meta> metas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<PlanoTreino> planoTreinos = new ArrayList<>();

    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private PerfilFisico perfilFisico;

    @ManyToMany
    @JoinTable(
            name = "usuario_role_tb",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Usuario() {
    }

    public Usuario(
            Long id,
            String nome,
            String email,
            String senha,
            PerfilFisico perfilFisico
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfilFisico = perfilFisico;
    }

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

    public void setPerfilFisico(PerfilFisico perfilFisico) {
        this.perfilFisico = perfilFisico;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Usuario usuario = (Usuario) o;

        return id != null && Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }
}
