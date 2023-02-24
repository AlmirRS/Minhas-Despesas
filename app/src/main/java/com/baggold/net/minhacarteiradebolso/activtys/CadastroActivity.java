package com.baggold.net.minhacarteiradebolso.activtys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.baggold.net.minhacarteiradebolso.config.ConfiguracaoFirabese;
import com.baggold.net.minhacarteiradebolso.databinding.ActivityCadastroBinding;
import com.baggold.net.minhacarteiradebolso.helper.Base64Custom;
import com.baggold.net.minhacarteiradebolso.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Objects;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonCd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarCampos();
            }
        });

        binding.textVoltarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public void validarCampos(){
        String nome = binding.editNome.getText().toString();
        String email = binding.editEmail.getText().toString();
        String senha = binding.editSenha.getText().toString();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!senha.isEmpty()) {

                    Usuario usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);

                    cadastrarUsuario( usuario );

                }else{
                    Toast.makeText(CadastroActivity.this, "Adicione uma senha", Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(CadastroActivity.this, "Adicione um email", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(CadastroActivity.this, "Adicione um nome", Toast.LENGTH_LONG).show();
        }
    }

    public void cadastrarUsuario(Usuario usuario) {

        FirebaseAuth authentication = ConfiguracaoFirabese.getFirebaseAutenticacao();
        authentication.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(CadastroActivity.this, LoginActivity.class));

                            String idUsuario = Base64Custom.codificarBase64( usuario.getEmail());
                            usuario.setIdUsuario( idUsuario );
                            usuario.salvar();
                            finish();

                        }else{

                            String excecao = "";
                            try {
                                throw Objects.requireNonNull(task.getException());
                            }catch (FirebaseAuthWeakPasswordException e) {
                                excecao = "Digite uma senha com no minimo 6 caracteres";
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                excecao = "Por favor digite um email válido";
                            }catch (FirebaseAuthUserCollisionException e){
                                excecao = "Esta conta ja foi cadastrada";
                            }catch (Exception e){
                                excecao = "Erro ao cadastrar usuario: " + e.getMessage();
                                e.printStackTrace();
                            }
                          Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}