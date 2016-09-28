package br.com.trasmontano.trasmonmobile.domain.model;

/**
 * Created by fmuniz on 17/06/2016.
 */
public class Foto {
    private String NomeImagem;
    private String ExtensaoImagem;
    private String Imagem;

    public String getNomeImagem() {
        return NomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        NomeImagem = nomeImagem;
    }

    public String getExtensaoImagem() {
        return ExtensaoImagem;
    }

    public void setExtensaoImagem(String extensaoImagem) {
        ExtensaoImagem = extensaoImagem;
    }

    public String getImagem() {
        return Imagem;
    }

    public void setImagem(String imagem) {
        Imagem = imagem;
    }
}
