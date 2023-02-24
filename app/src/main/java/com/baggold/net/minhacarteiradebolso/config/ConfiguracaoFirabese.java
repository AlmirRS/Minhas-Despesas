package com.baggold.net.minhacarteiradebolso.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirabese {

    private static FirebaseAuth autenticacao;
    private static DatabaseReference firebase;

    //RealtimeDataBase
    public static DatabaseReference getFirebaseDatabase() {
        if (firebase == null ) {
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;
    }

    //FirebaseAuth
    public static FirebaseAuth getFirebaseAutenticacao() {
        if (autenticacao == null ) {
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}
