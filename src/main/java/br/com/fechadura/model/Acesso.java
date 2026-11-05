package br.com.fechadura.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_acesso")
public class Acesso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usuarioInformado;
    private String resultado;
    private String detalhe;
    private LocalDateTime dataHora;

    public Acesso() {}
    public Acesso(String usuarioInformado, String resultado, String detalhe) {
        this.usuarioInformado = usuarioInformado;
        this.resultado = resultado;
        this.detalhe = detalhe;
        this.dataHora = LocalDateTime.now();
    }
    public Long getId() { return id; }
    public String getUsuarioInformado() { return usuarioInformado; }
    public String getResultado() { return resultado; }
    public String getDetalhe() { return detalhe; }
    public LocalDateTime getDataHora() { return dataHora; }
}
