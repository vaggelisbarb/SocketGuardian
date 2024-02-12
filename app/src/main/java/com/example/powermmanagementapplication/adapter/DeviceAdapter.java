package com.example.powermmanagementapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.powermmanagementapplication.activity.DeviceDetailActivity;
import com.example.powermmanagementapplication.databinding.DeviceViewholderListBinding;
import com.example.powermmanagementapplication.domain.DeviceDomain;

import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private ArrayList<DeviceDomain> devices;
    private Context context;

    DeviceViewholderListBinding binding;

    public DeviceAdapter(ArrayList<DeviceDomain> devices) {
        this.devices = devices;
    }

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DeviceViewholderListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder holder, int position) {
        binding.deviceTitleTextView.setText(devices.get(position).getDeviceName());
        binding.connectivityTextView.setText(devices.get(position).getConnectivity());
        binding.updatedTextView.setText(devices.get(position).getLastUpdate());
        binding.pairedTextView.setText(devices.get(position).initPairingStatus());


        int drawableResourced = holder.itemView.getResources().getIdentifier(devices.get(position).getPicUrl(),
                "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableResourced)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(binding.imageView);

        int drawableStatusResourced = holder.itemView.getResources().getIdentifier(devices.get(position).getStatusIcon(),
                "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableStatusResourced)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(binding.statusImageView);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DeviceDetailActivity.class);
            intent.putExtra("deviceObj", devices.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(DeviceViewholderListBinding binding) {
            super(binding.getRoot());
        }
    }
}
