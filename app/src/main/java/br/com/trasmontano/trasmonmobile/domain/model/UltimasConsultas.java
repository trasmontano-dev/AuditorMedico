package br.com.trasmontano.trasmonmobile.domain.model;

/**
 * Created by fmuniz on 15/07/2016.
 */
public class UltimasConsultas {
    private String Termo;
    private String DataEmissao;
    private String CaraterAtendimento;
    private String Rede;

    public String getTermo() {
        return Termo;
    }

    public void setTermo(String termo) {
        Termo = termo;
    }

    public String getDataEmissao() {
        return DataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        DataEmissao = dataEmissao;
    }

    public String getCaraterAtendimento() {
        return CaraterAtendimento;
    }

    public void setCaraterAtendimento(String caraterAtendimento) {
        CaraterAtendimento = caraterAtendimento;
    }

    public String getRede() {
        return Rede;
    }

    public void setRede(String rede) {
        Rede = rede;
    }
}
