package com.baggold.net.minhacarteiradebolso.model;

import com.baggold.net.minhacarteiradebolso.config.ConfiguracaoFirabese;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private String idUsuario;
    private String fotoAtual;
    private Double receitaTotal = 0.00;
    private Double despesaTotal = 0.00;

    public Usuario() {
    }

    public void salvar() {
        DatabaseReference firebase = ConfiguracaoFirabese.getFirebaseDatabase();
        firebase.child("usuarios")
                .child(this.idUsuario)
                .setValue(this);
    }

    public Double getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(Double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public Double getDespesaTotal() {
        return despesaTotal;
    }

    public void setDespesaTotal(Double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String id) {
        this.idUsuario = id;
    }

    @Exclude
    public String getFotoAtual() {
        return fotoAtual;
    }

    public void setFotoAtual(String fotoAtual) {
        this.fotoAtual = fotoAtual;
    }
}
