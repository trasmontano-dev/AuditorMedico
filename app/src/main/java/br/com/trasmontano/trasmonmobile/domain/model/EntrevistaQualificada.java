package br.com.trasmontano.trasmonmobile.domain.model;

import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by fmuniz on 12/08/2016.
 */
public class EntrevistaQualificada {
    private Integer Id;
    private String Proposta;
    private String Matricula;
    private String CodDependente;
    private String Nome;
    private Float Peso;
    private Float Altura;
    private Float IMC;
    private String DataNascimento;
    private String CPF;
    private String RG;
    private String DataAdmissao;
    private String DataRealizacao;
    private Integer Idade;

    private List<EntrevistaQualificadaQuestao> Questoes;
    private List<EntrevistaQualificadaCID> CIDs;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getProposta() {
        return Proposta;
    }

    public void setProposta(String proposta) {
        Proposta = proposta;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String getCodDependente() {
        return CodDependente;
    }

    public void setCodDependente(String codDependente) {
        CodDependente = codDependente;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Float getPeso() {
        return Peso;
    }

    public void setPeso(Float peso) {
        Peso = peso;
    }

    public Float getAltura() {
        return Altura;
    }

    public void setAltura(Float altura) {
        Altura = altura;
    }

    public Float getIMC() {
        return IMC;
    }

    public void setIMC(Float IMC) {
        this.IMC = IMC;
    }

    public String getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        DataNascimento = dataNascimento;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public Integer getIdade() {
        return Idade;
    }

    public void setIdade(Integer idade) {
        Idade = idade;
    }

    public List<EntrevistaQualificadaQuestao> getQuestoes() {
        return Questoes;
    }

    public void setQuestoes(List<EntrevistaQualificadaQuestao> questoes) {
        Questoes = questoes;
    }

    public List<EntrevistaQualificadaCID> getCIDs() {
        return CIDs;
    }

    public void setCIDs(List<EntrevistaQualificadaCID> CIDs) {
        this.CIDs = CIDs;
    }

    public String getDataAdmissao() {
        return DataAdmissao;
    }

    public void setDataAdmissao(String dataAdmissao) {
        DataAdmissao = dataAdmissao;
    }

    public String getDataRealizacao() {
        return DataRealizacao;
    }

    public void setDataRealizacao(String dataRealizacao) {
        DataRealizacao = dataRealizacao;
    }
}
