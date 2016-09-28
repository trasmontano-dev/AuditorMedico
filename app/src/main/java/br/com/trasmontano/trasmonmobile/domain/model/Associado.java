package br.com.trasmontano.trasmonmobile.domain.model;

import com.google.gson.annotations.Expose;

/**
 * Created by fmuniz on 16/06/2016.
 */
public class Associado {

    private String Nome;
    private String CartaoNacionalSaude;
    private String DataNascimento;
    private String Regiao;
    private String Situacao;
    private String Categoria;

    public String getSituacao() {
        return Situacao;
    }

    public void setSituacao(String situacao) {
        Situacao = situacao;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCartaoNacionalSaude() {
        return CartaoNacionalSaude;
    }

    public void setCartaoNacionalSaude(String cartaoNacionalSaude) {
        CartaoNacionalSaude = cartaoNacionalSaude;
    }

    public String getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        DataNascimento = dataNascimento;
    }

    public String getRegiao() {
        return Regiao;
    }

    public void setRegiao(String regiao) {
        Regiao = regiao;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }
}
