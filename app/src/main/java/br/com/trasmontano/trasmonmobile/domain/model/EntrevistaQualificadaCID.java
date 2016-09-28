package br.com.trasmontano.trasmonmobile.domain.model;

/**
 * Created by fmuniz on 25/08/2016.
 */
public class EntrevistaQualificadaCID {

    private Integer Item;
    private String Parecer;
    private String CID;
    private String Descricao;

    public Integer getItem() {
        return Item;
    }

    public void setItem(Integer item) {
        Item = item;
    }

    public String getParecer() {
        return Parecer;
    }

    public void setParecer(String parecer) {
        Parecer = parecer;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }
}
