package br.com.trasmontano.trasmonmobile.domain.model;

/**
 * Created by fmuniz on 17/06/2016.
 */
public class Usuario {
    private String Nome;
    private String Login;
    private String Departamento;
    private String CCusto;
    private String Foto;
    private String Permitido;

    public String getFoto() {
        return Foto;
    }

    public void setFoto(String foto) {
        Foto = foto;
    }

    public String getPermitido() {
        return Permitido;
    }

    public void setPermitido(String permitido) {
        Permitido = permitido;
    }

    public String getCCusto() {
        return CCusto;
    }

    public void setCCusto(String CCusto) {
        this.CCusto = CCusto;
    }

    public String getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(String departamento) {
        Departamento = departamento;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }
}
