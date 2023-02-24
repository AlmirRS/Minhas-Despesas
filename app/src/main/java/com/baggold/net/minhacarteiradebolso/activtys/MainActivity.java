package com.baggold.net.minhacarteiradebolso.activtys;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.baggold.net.minhacarteiradebolso.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());


    }
}