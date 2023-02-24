package com.baggold.net.minhacarteiradebolso.activtys;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.baggold.net.minhacarteiradebolso.config.ConfiguracaoFirabese;
import com.baggold.net.minhacarteiradebolso.databinding.ActivityReceitasBinding;
import com.baggold.net.minhacarteiradebolso.helper.Base64Custom;
import com.baggold.net.minhacarteiradebolso.helper.DataCustomizada;
import com.baggold.net.minhacarteiradebolso.model.Movimentacao;
import com.baggold.net.minhacarteiradebolso.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ReceitasActivity extends AppCompatActivity {

    private final DatabaseReference firebaseRef = ConfiguracaoFirabese.getFirebaseDatabase();
    private final FirebaseAuth autenticacao = ConfiguracaoFirabese.getFirebaseAutenticacao();
    private Double receitaTotal;

    private ActivityReceitasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReceitasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        //Mostrar data atual mes e ano
        binding.editDataR.setText(DataCustomizada.dataAtual());
        //Metodo para recuperar a receitaTotal
        recuperarReceitaTotal();

        binding.btnSalvarR.setOnClickListener(v -> {

            String valor = binding.campoValorR.getText().toString();
            String data = binding.editDataR.getText().toString();
            String categoria = binding.editCategoriaR.getText().toString();
            String descricao = binding.editDescricaoR.getText().toString();

            if (!valor.isEmpty()) {
                if (!data.isEmpty()) {
                    if (!categoria.isEmpty()) {
                        if (!descricao.isEmpty()) {

                            salvarReceita();

                        } else {
                            Toast.makeText(ReceitasActivity.this, "Descrição não foi preenchida", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ReceitasActivity.this, "Categoria não foi preenchida", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ReceitasActivity.this, "Data não foi preenchida", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ReceitasActivity.this, "Valor não foi preenchido", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void salvarReceita() {
        Movimentacao movimentacao = new Movimentacao();

        String data = binding.editDataR.getText().toString();
        Double valorRecuperado = Double.parseDouble(binding.campoValorR.getText().toString());

        movimentacao.setValor( valorRecuperado );
        movimentacao.setCategoria(binding.editCategoriaR.getText().toString());
        movimentacao.setDescricao(binding.editDescricaoR.getText().toString());
        movimentacao.setTipo("r");
        movimentacao.setData( data );
        Double despesaAtualizada = receitaTotal + valorRecuperado;
        atualizarReceita(despesaAtualizada);

        movimentacao.salvar( data );
    }

    public void recuperarReceitaTotal() {

        //Recuperando o email do usuario atual de decodificando
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);

        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void atualizarReceita(Double receita) {
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);

        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);
        usuarioRef.child("receitaTotal").setValue( receita );

    }
}