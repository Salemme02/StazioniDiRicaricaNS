package it.univaq.app.stazionidiricaricans;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import it.univaq.app.stazionidiricaricans.databinding.ActivityDetailBinding;
import it.univaq.app.stazionidiricaricans.model.Charger;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_CHARGER = "extra_charger";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Charger charger = (Charger) getIntent().getSerializableExtra(EXTRA_CHARGER);
        if(charger != null)
            binding.setCharger(charger);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green4));
    }


}
