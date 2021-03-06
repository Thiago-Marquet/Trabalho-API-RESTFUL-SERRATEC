package org.serratec.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProdutoInserirDTO {
    
    @NotBlank
    @Size(max = 40, min = 3)
    private String nome;
    
    @NotNull
    @DecimalMin(value = "0.10", message = "Valor Unitario deve ser maior do que zero (0)")
    @Digits(integer=18, fraction=2, message = "Valor unitário dever ter apenas duas casas decimais")
    private BigDecimal valorUnitario;
    
    @NotNull
    private String categoria;

    private String foto;

    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }
    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
