package com.baggold.net.minhacarteiradebolso.model;

import com.baggold.net.minhacarteiradebolso.config.ConfiguracaoFirabese;
import com.baggold.net.minhacarteiradebolso.helper.Base64Custom;
import com.baggold.net.minhacarteiradebolso.helper.DataCustomizada;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movimentacao {

    private String data;
    private String categoria;
    private String descricao;
    private String tipo;
    private Double valor;

    public Movimentacao() {
    }

    public void salvar(String dataEscolhida) {

        //Bucando o email do usuario e codificando
        FirebaseAuth autenticacao = ConfiguracaoFirabese.getFirebaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64( autenticacao.getCurrentUser().getEmail());

        //Capiturando o mes e o ano atual ja modificado para exibir sem barras "/"
        String mesAno = DataCustomizada.mesAnoDataEscolhida( dataEscolhida );

        DatabaseReference firebase = ConfiguracaoFirabese.getFirebaseDatabase();
        firebase.child("movimentacao")
                .child(idUsuario)
                .child(mesAno)
                .push()
                .setValue(this);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
