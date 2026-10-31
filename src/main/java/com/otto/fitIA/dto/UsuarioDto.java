
package com.otto.fitIA.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados básicos do usuário")
public class UsuarioDto {

    @Schema(
            description = "Identificador do usuário",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nome completo do usuário",
            example = "Carlos Luis"
    )
    private String nome;

    @Schema(
            description = "Endereço de e-mail do usuário",
            example = "maicon@email.com"
    )
    private String email;

    public UsuarioDto() {

    }

    public UsuarioDto(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
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
}
