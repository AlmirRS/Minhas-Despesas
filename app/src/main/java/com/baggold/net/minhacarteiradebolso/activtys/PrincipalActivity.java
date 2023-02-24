package com.baggold.net.minhacarteiradebolso.activtys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.baggold.net.minhacarteiradebolso.R;
import com.baggold.net.minhacarteiradebolso.databinding.ActivityPrincipalBinding;
import com.google.firebase.auth.FirebaseAuth;

public class PrincipalActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityPrincipalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Configuração do calendario para data atual
        configuraCalendarView();
        //toolbar
        setSupportActionBar(binding.toolbar);


        binding.menuReceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, ReceitasActivity.class));
            }
        });

        binding.menuDespesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrincipalActivity.this, DespesasActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.deslogar) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void configuraCalendarView() {
        CharSequence meses[] = {"Janeiro", "fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        binding.include.calendarView.setTitleMonths(meses);

        CharSequence semana[] = {"Seg", "Ter", "Qua", "Qui", "Sex", "Sab", "Dom"};
        binding.include.calendarView.setWeekDayLabels(semana);

        binding.include.calendarView.setOnMonthChangedListener((widget, date) -> Log.i("data","mes: " + (date.getMonth() + 1) + date.getYear()));
    }
}