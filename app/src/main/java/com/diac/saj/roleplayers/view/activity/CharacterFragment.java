package com.diac.saj.roleplayers.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diac.saj.roleplayers.R;
import com.diac.saj.roleplayers.databinding.FragmentCharacterBinding;
import com.diac.saj.roleplayers.model.entity.CharacterCR;
import com.diac.saj.roleplayers.view.adapter.CharacterAdapter;
import com.diac.saj.roleplayers.viewmodel.CharacterViewModel;

import java.util.List;

public class CharacterFragment extends Fragment {

    private FragmentCharacterBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentCharacterBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvCharacters = binding.rvCharacters;
        rvCharacters.setLayoutManager(new LinearLayoutManager(requireActivity()));

        CharacterViewModel cvm = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);
        CharacterAdapter characterAdapter = new CharacterAdapter(requireActivity());

        rvCharacters.setAdapter(characterAdapter);

        LiveData<List<CharacterCR>> listaCharacters = cvm.getAllCharacter();
        listaCharacters.observe(requireActivity(), characterAdapter::setCharacterList);

        binding.fab.setOnClickListener(view1 -> NavHostFragment.findNavController(CharacterFragment.this)
                .navigate(R.id.action_CharacterFragment_to_AddFragment));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}