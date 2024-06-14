package it.univaq.app.stazionidiricaricans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import it.univaq.app.stazionidiricaricans.databinding.FragmentListBinding;
import it.univaq.app.stazionidiricaricans.model.Charger;
import it.univaq.app.stazionidiricaricans.service.MainViewModel;

public class ListFragment extends Fragment {

    private FragmentListBinding binding;
    private MainViewModel mainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mainViewModel.getChargers().observe(getViewLifecycleOwner(), new Observer<List<Charger>>() {
            @Override
            public void onChanged(List<Charger> chargers) {
                binding.recyclerView.setAdapter(new ChargerAdapter(chargers));
            }
        });
    }
}
