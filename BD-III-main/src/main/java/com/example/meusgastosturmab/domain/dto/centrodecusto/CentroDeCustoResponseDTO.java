package com.example.meusgastosturmab.domain.dto.centrodecusto;

public class CentroDeCustoResponseDTO {
    private Long id;
    private String descricao;
    private String observaco;
    
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
    public String getObservaco() {
        return observaco;
    }
    public void setObservaco(String observaco) {
        this.observaco = observaco;
    }
}
