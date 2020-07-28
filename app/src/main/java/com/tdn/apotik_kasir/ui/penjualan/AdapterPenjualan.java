package com.tdn.apotik_kasir.ui.penjualan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.tdn.apotik_kasir.R;
import com.tdn.apotik_kasir.core.callback.AdapterClicked;
import com.tdn.apotik_kasir.databinding.ItemObatBinding;
import com.tdn.domain.object.ObatObject;
import com.tdn.domain.object.PenjualanObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdapterPenjualan extends RecyclerView.Adapter<AdapterPenjualan.MyViewHolder> {
    private List<PenjualanObject> obatObjectList = new ArrayList<>();
    private Context context;
    private AdapterClicked adapterClicked;

    public AdapterPenjualan(Context context, AdapterClicked adapterClicked) {
        this.context = context;
        this.adapterClicked = adapterClicked;
    }

    @NonNull
    @Override
    public AdapterPenjualan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemObatBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_obat, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPenjualan.MyViewHolder holder, int position) {
        PenjualanObject o = obatObjectList.get(position);
        holder.binding.tvNamaItem.setText(o.getPenjualanIdTransaksi());
        holder.binding.tvSatuanJual.setText(o.getPenjualanTanggal());
        holder.binding.tvStok.setText("Rp" + o.getPenjualanSubtotal());
        holder.binding.lyItem.setOnClickListener(view -> {

            adapterClicked.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return obatObjectList == null ? 0 : obatObjectList.size();
    }

    public void setData(List<PenjualanObject> TabunganModels) {
        if (this.obatObjectList == null) {
            this.obatObjectList.addAll(TabunganModels);
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return AdapterPenjualan.this.obatObjectList.size();
                }

                @Override
                public int getNewListSize() {
                    return TabunganModels.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return AdapterPenjualan.this.obatObjectList.get(oldItemPosition).getPenjualanId() == TabunganModels.get(newItemPosition).getPenjualanId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    PenjualanObject lama = TabunganModels.get(oldItemPosition);
                    PenjualanObject baru = TabunganModels.get(newItemPosition);
                    return lama == baru && Objects.equals(lama, baru);
                }
            });
            this.obatObjectList = TabunganModels;
            result.dispatchUpdatesTo(this);

        }

    }

    public PenjualanObject getFromPosition(int posisi) {
        return obatObjectList.get(posisi);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemObatBinding binding;

        public MyViewHolder(@NonNull ItemObatBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
