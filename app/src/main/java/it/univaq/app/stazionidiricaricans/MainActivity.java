package it.univaq.app.stazionidiricaricans;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import it.univaq.app.stazionidiricaricans.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater()); //mi trovo in una activity quindi posso usare direttamente il layout inflater
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green4));

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if(navHostFragment != null) {
            NavController controller = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.bottomNavigationView, controller); //effettua il binding tra il bottomNavView e il controller del NavFragment
        }


    }
}