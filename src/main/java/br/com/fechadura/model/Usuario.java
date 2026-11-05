package br.com.fechadura.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nomeUsuario;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senhaHash;

    @Column(nullable = false)
    private String perfil = "USUARIO";

    @Column(nullable = false)
    private boolean ativo = true;

    public Usuario() {}
    public Usuario(String nomeUsuario, String nome, String senhaHash, String perfil) {
        this.nomeUsuario = nomeUsuario;
        this.nome = nome;
        this.senhaHash = senhaHash;
        this.perfil = perfil;
        this.ativo = true;
    }
    public Long getId() { return id; }
    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
