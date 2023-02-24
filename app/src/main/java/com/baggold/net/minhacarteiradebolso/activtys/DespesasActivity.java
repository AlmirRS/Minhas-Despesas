package com.baggold.net.minhacarteiradebolso.activtys;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.baggold.net.minhacarteiradebolso.config.ConfiguracaoFirabese;
import com.baggold.net.minhacarteiradebolso.databinding.ActivityDespesasBinding;
import com.baggold.net.minhacarteiradebolso.helper.Base64Custom;
import com.baggold.net.minhacarteiradebolso.helper.DataCustomizada;
import com.baggold.net.minhacarteiradebolso.model.Movimentacao;
import com.baggold.net.minhacarteiradebolso.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DespesasActivity extends AppCompatActivity {

    private final DatabaseReference firebaseRef = ConfiguracaoFirabese.getFirebaseDatabase();
    private final FirebaseAuth autenticacao = ConfiguracaoFirabese.getFirebaseAutenticacao();
    private Double despesaTotal;
    private ActivityDespesasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDespesasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        //Preenche o campo de data com a data atual
        binding.editDataD.setText(DataCustomizada.dataAtual());
        //Metodo que reupera a despesaTotal
        recuperarDespesaTotal();

        binding.btnSalvarD.setOnClickListener(v -> {

            String valor = binding.campoValorD.getText().toString();
            String data = binding.editDataD.getText().toString();
            String categoria = binding.editCategoriaD.getText().toString();
            String descricao = binding.editDescricaoD.getText().toString();

            if (!valor.isEmpty()) {
                if (!data.isEmpty()) {
                    if (!categoria.isEmpty()) {
                        if (!descricao.isEmpty()) {

                            salvarDespesa();

                        } else {
                            Toast.makeText(DespesasActivity.this, "Descrição não foi preenchida", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DespesasActivity.this, "Categoria não foi preenchida", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DespesasActivity.this, "Data não foi preenchida", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DespesasActivity.this, "Valor não foi preenchido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void salvarDespesa() {
        Movimentacao movimentacao = new Movimentacao();

        String data = binding.editDataD.getText().toString();
        Double valorRecuperado = Double.parseDouble(binding.campoValorD.getText().toString());

        movimentacao.setValor(valorRecuperado);
        movimentacao.setCategoria(binding.editCategoriaD.getText().toString());
        movimentacao.setDescricao(binding.editDescricaoD.getText().toString());
        movimentacao.setData(data);
        movimentacao.setTipo("d");

        Double despesaAtualizada = despesaTotal + valorRecuperado;
        atualizarDespesa(despesaAtualizada);

        movimentacao.salvar( data );
    }

    public void recuperarDespesaTotal() {

        //Recuperando o email do usuario atual de decodificando
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);

        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void atualizarDespesa(Double despesa) {
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);

        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);
        usuarioRef.child("despesaTotal").setValue(despesa);

    }
}