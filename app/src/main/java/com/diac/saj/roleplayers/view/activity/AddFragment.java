package com.diac.saj.roleplayers.view.activity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavAction;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.diac.saj.roleplayers.R;
import com.diac.saj.roleplayers.databinding.FragmentAddBinding;
import com.diac.saj.roleplayers.model.entity.Race;
import com.diac.saj.roleplayers.model.entity.RoleCharacter;
import com.diac.saj.roleplayers.model.entity.RoleClass;
import com.diac.saj.roleplayers.viewmodel.CharacterViewModel;
import com.diac.saj.roleplayers.viewmodel.ClassViewModel;
import com.diac.saj.roleplayers.viewmodel.RaceViewModel;

import java.util.List;


public class AddFragment extends Fragment {

    private FragmentAddBinding binding;
    CharacterViewModel cvm;
    static boolean firstTime = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //binding.ivADDcharacter.setImageResource(R.drawable.ic_baseline_question_mark_24);
        getViewModel();
        defineAddListener();
        defineOnFocusListener();
    }

    private void defineOnFocusListener() {
        binding.spADDclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (binding.spADDclass.getSelectedItemPosition()) {
                    case 0:
                        binding.ivADDcharacter.setImageResource(R.drawable.ic_baseline_question_mark_24);
                        break;
                    case 1:
                        binding.ivADDcharacter.setImageResource(R.drawable.barbarian);
                        break;
                    case 2:
                        binding.ivADDcharacter.setImageResource(R.drawable.bard);
                        break;
                    case 3:
                        binding.ivADDcharacter.setImageResource(R.drawable.cleric);
                        break;
                    case 4:
                        binding.ivADDcharacter.setImageResource(R.drawable.druid);
                        break;
                    case 5:
                        binding.ivADDcharacter.setImageResource(R.drawable.fighter);
                        break;
                    case 6:
                        binding.ivADDcharacter.setImageResource(R.drawable.monk);
                        break;
                    case 7:
                        binding.ivADDcharacter.setImageResource(R.drawable.paladin);
                        break;
                    case 8:
                        binding.ivADDcharacter.setImageResource(R.drawable.ranger);
                        break;
                    case 9:
                        binding.ivADDcharacter.setImageResource(R.drawable.rogue);
                        break;
                    case 10:
                        binding.ivADDcharacter.setImageResource(R.drawable.sorcerer);
                        break;
                    case 11:
                        binding.ivADDcharacter.setImageResource(R.drawable.warlock);
                        break;
                    case 12:
                        binding.ivADDcharacter.setImageResource(R.drawable.wizard);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private void defineAddListener() {
        binding.btAdd.setOnClickListener(view -> {
            RoleCharacter character = getCharacter();
            if  (character.isValid()){
                addCharacter(character);
            } else {
                Toast.makeText(requireActivity(), "some fields aren't valid", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCharacter(RoleCharacter character) {
        cvm.insertCharacter(character);
    }

    private RoleCharacter getCharacter() {
        String name, level, creation, state, strenght, dexterity, constitution, intelligence,
                wisdom, charisma;

        name = binding.tietADDname.getText().toString();
        level = binding.tietADDlevel.getText().toString();
        creation = binding.tietADDcreation.getText().toString();
        state = binding.tietADDstate.getText().toString();
        strenght = binding.tietADDstrength.getText().toString();
        dexterity = binding.tietADDdexterity.getText().toString();
        constitution = binding.tietADDconstitution.getText().toString();
        intelligence = binding.tietADDintelligence.getText().toString();
        wisdom = binding.tietADDwisdom.getText().toString();
        charisma = binding.tietADDcharisma.getText().toString();

        RoleClass roleClass = (RoleClass) binding.spADDclass.getSelectedItem();
        Race race = (Race) binding.spADDrace.getSelectedItem();
        RoleCharacter roleCharacter = new RoleCharacter();
        roleCharacter.name = name;
        roleCharacter.creation = creation;
        roleCharacter.state = state;

        try {
            roleCharacter.level = Integer.parseInt(level);
            roleCharacter.strength = Integer.parseInt(strenght);
            roleCharacter.dexterity = Integer.parseInt(dexterity);
            roleCharacter.constitution = Integer.parseInt(constitution);
            roleCharacter.intelligence = Integer.parseInt(intelligence);
            roleCharacter.wisdom = Integer.parseInt(wisdom);
            roleCharacter.charisma = Integer.parseInt(charisma);
        } catch (NumberFormatException nfe) {
            roleCharacter.level = -1;
            roleCharacter.strength = -1;
            roleCharacter.dexterity = -1;
            roleCharacter.constitution = -1;
            roleCharacter.intelligence = -1;
            roleCharacter.wisdom = -1;
            roleCharacter.charisma = -1;
        }

        roleCharacter.idclass = roleClass.id;
        roleCharacter.idrace = race.id;

        return roleCharacter;
    }



    private void getViewModel(){
        cvm = new ViewModelProvider(requireActivity()).get(CharacterViewModel.class);

        cvm.getInsertResults().observe(requireActivity(), list -> {
            if (list.get(0) > 0){
                if (firstTime){
                    firstTime = false;
                    alert();
                } else {
                    cleanFields();
                }
            } else {
                Toast.makeText(getContext(), "error 10", Toast.LENGTH_SHORT).show();
            }
        });

        ClassViewModel classViewModel = new ViewModelProvider(requireActivity()).get(ClassViewModel.class);
        classViewModel.getClasses().observe(requireActivity(), roleClasses -> {

            if (!roleClasses.get(0).name.equals("<<select a class>>")){
                RoleClass roleClass = new RoleClass();
                roleClass.id = 0;
                roleClass.name = "<<select a class>>";
                roleClasses.add(0, roleClass);
            }

            ArrayAdapter<RoleClass> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, roleClasses);
            binding.spADDclass.setAdapter(adapter);
        });

        RaceViewModel rvm = new ViewModelProvider(requireActivity()).get(RaceViewModel.class);
        rvm.getRaces().observe(requireActivity(), races -> {

            if (!races.get(0).name.equals("<<select a race>>")) {
                Race race = new Race();
                race.id = 0;
                race.name = "<<select a race>>";
                races.add(0, race);
            }

            ArrayAdapter<Race> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, races);
            binding.spADDrace.setAdapter(adapter);
        });
    }

    private void cleanFields() {
        binding.tietADDname.setText("");
        binding.tietADDlevel.setText("");
        binding.tietADDcreation.setText("");
        binding.tietADDstate.setText("");
        binding.tietADDstrength.setText("");
        binding.tietADDdexterity.setText("");
        binding.tietADDconstitution.setText("");
        binding.tietADDintelligence.setText("");
        binding.tietADDwisdom.setText("");
        binding.tietADDcharisma.setText("");

        binding.spADDclass.setSelection(0);
        binding.spADDrace.setSelection(0);

    }



    private void alert() {
        AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());
        builder.setTitle("Insertar mas?")
                .setMessage("El personaje se ha insertado correctamente, desea seguir agregando pokemons?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton( android.R.string.ok, (dialog, which) -> cleanFields());


        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        alertDialog.setOnShowListener(dialog -> {
            Button button_negative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            button_negative.setOnClickListener(v -> {
                alertDialog.dismiss();
                NavHostFragment.findNavController(this).popBackStack();
            });
        });
        alertDialog.show();

    }
}
