package br.com.trasmontano.trasmonmobile.domain.model;

/**
 * Created by fmuniz on 18/06/2016.
 */
public class ItemCarencia {
    private int CodigoCarenciaItem;
    private String DescricaoCarencia;
    private String Situacao;
    private String DataCarencia;

    public String getDataCarencia() {
        return DataCarencia;
    }

    public void setDataCarencia(String dataCarencia) {
        DataCarencia = dataCarencia;
    }

    public int getCodigoCarenciaItem() {
        return CodigoCarenciaItem;
    }

    public void setCodigoCarenciaItem(int codigoCarenciaItem) {
        CodigoCarenciaItem = codigoCarenciaItem;
    }

    public String getDescricaoCarencia() {
        return DescricaoCarencia;
    }

    public void setDescricaoCarencia(String descricaoCarencia) {
        DescricaoCarencia = descricaoCarencia;
    }

    public String getSituacao() {
        return Situacao;
    }

    public void setSituacao(String situacao) {
        Situacao = situacao;
    }
}
