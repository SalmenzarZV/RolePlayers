package com.diac.saj.roleplayers.view.activity;

import android.os.Bundle;

import com.diac.saj.roleplayers.R;

import com.diac.saj.roleplayers.model.entity.CharacterCR;
import com.diac.saj.roleplayers.view.adapter.CharacterAdapter;
import com.diac.saj.roleplayers.viewmodel.CharacterViewModel;

import androidx.appcompat.app.AppCompatActivity;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.diac.saj.roleplayers.databinding.ActivityMainBinding;
import com.diac.saj.roleplayers.viewmodel.ClassViewModel;
import com.diac.saj.roleplayers.viewmodel.RaceViewModel;

import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private CharacterViewModel cvm;
    private RaceViewModel rvm;
    private ClassViewModel clssvm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        final NavController navController = navHostFragment.getNavController();


        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        initialize();

    }

    private void initialize() {

        RecyclerView rvCharacters = findViewById(R.id.rvCharacters);
        rvCharacters.setLayoutManager(new LinearLayoutManager(this));

        cvm = new ViewModelProvider(this).get(CharacterViewModel.class);
        rvm =  new ViewModelProvider(this).get(RaceViewModel.class);
        clssvm =  new ViewModelProvider(this).get(ClassViewModel.class);
        CharacterAdapter characterAdapter = new CharacterAdapter(this);

        rvCharacters.setAdapter(characterAdapter);

        LiveData<List<CharacterCR>> listaCharacters = cvm.getAllCharacter();
        listaCharacters.observe(this, characterAdapter::setCharacterList);

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}