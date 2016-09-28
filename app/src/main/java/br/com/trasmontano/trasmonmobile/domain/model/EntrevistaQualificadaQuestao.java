package br.com.trasmontano.trasmonmobile.domain.model;

/**
 * Created by fmuniz on 25/08/2016.
 */
public class EntrevistaQualificadaQuestao {

    private String Sequencia;
    private String Questao;
    private String Resposta;
    private String Tempo;
    private String Alegacao;

    public String getSequencia() {
        return Sequencia;
    }

    public void setSequencia(String sequencia) {
        Sequencia = sequencia;
    }

    public String getQuestao() {
        return Questao;
    }

    public void setQuestao(String questao) {
        Questao = questao;
    }

    public String getResposta() {
        return Resposta;
    }

    public void setResposta(String resposta) {
        Resposta = resposta;
    }

    public String getTempo() {
        return Tempo;
    }

    public void setTempo(String tempo) {
        Tempo = tempo;
    }

    public String getAlegacao() {
        return Alegacao;
    }

    public void setAlegacao(String alegacao) {
        Alegacao = alegacao;
    }
}
