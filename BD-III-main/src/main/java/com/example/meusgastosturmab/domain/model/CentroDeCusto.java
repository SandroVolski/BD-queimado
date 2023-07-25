package com.example.meusgastosturmab.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CentroDeCusto")
public class CentroDeCusto {
    @Id // joga/cria o codigo como tabela no BD
    @GeneratedValue(strategy = GenerationType.IDENTITY) // gera o id automatico /o banco q cuida e n cria outro igual
    @Column(name = "idCentroDeCusto")
    private Long id;
    @Column(nullable = false) //nao pode ser falso
    private String descricao;
    @Column(columnDefinition = "TEXT") //cabe texto longo
    private String observacao;
    @ManyToOne
    @JoinColumn(name = "idUsuario") // guarda o idUsuario 
    private Usuario usuario;
    @ManyToMany(mappedBy = "centrosDeCustos") //ele q mapeia, junta as tabelas
    @JsonBackReference
    private List<Titulo> titulo;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public List<Titulo> getTitulo() {
        return titulo;
    }
    public void setTitulo(List<Titulo> titulo) {
        this.titulo = titulo;
    }

    
}
