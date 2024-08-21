package it.univaq.app.stazionidiricaricans;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.univaq.app.stazionidiricaricans.databinding.AdapterChargerBinding;
import it.univaq.app.stazionidiricaricans.model.Charger;


public class ChargerAdapter extends RecyclerView.Adapter<ChargerAdapter.ViewHolder> {

    private AdapterChargerBinding binding;

    private List<Charger> data;

    public ChargerAdapter(List<Charger> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ChargerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = AdapterChargerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChargerAdapter.ViewHolder holder, int position) {
        Charger c = data.get(position);
        holder.onBind(c);
    }

    @Override
    public int getItemCount() {
        if(data != null) return data.size();
        else return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private AdapterChargerBinding binding;

        public ViewHolder(AdapterChargerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.linearLayout.setOnClickListener(this); //forse linearLayout al posto di adapter
        }

        @Override
        public void onClick(View v) {
            Charger charger = data.get(getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putSerializable(DetailActivity.EXTRA_CHARGER, charger);
            Navigation.findNavController(v).navigate(R.id.action_menu_list_to_detailActivity, bundle);
        }

        public void onBind(Charger charger) {
            binding.setCharger(charger);
        }
    }
}