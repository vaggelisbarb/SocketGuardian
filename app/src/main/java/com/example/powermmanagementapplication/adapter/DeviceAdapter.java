package com.example.powermmanagementapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.powermmanagementapplication.repository.DeviceRepository;
import com.example.powermmanagementapplication.repository.callback.FirebaseUpdateDeviceCallback;
import com.example.powermmanagementapplication.ui.DeviceDetailActivity;
import com.example.powermmanagementapplication.databinding.DeviceViewholderListBinding;
import com.example.powermmanagementapplication.model.device.Device;
import com.example.powermmanagementapplication.utils.DeviceUiUtils;

import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private ArrayList<Device> devices;
    private Context context;
    private DeviceRepository deviceRepository;
    private DeviceViewholderListBinding binding;

    public DeviceAdapter(ArrayList<Device> devices) {
        this.devices = devices;
        deviceRepository = new DeviceRepository();
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
        binding.pairedTextView.setText(devices.get(position).getPairStatus());


        int drawableResourced = holder.itemView.getResources().getIdentifier(DeviceUiUtils.generateDeviceIcon(devices.get(position).getConnectivity()),
                "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableResourced)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(binding.imageView);

        Log.i("Drawable", DeviceUiUtils.generateDeviceIcon(devices.get(position).getConnectivity()));

        int drawableStatusResourced = holder.itemView.getResources().getIdentifier(DeviceUiUtils.generateStatusIcon(devices.get(position).getDeviceStatus()),
                "drawable", holder.itemView.getContext().getPackageName());

        Log.i("Drawable", DeviceUiUtils.generateStatusIcon(devices.get(position).getDeviceStatus()));

                Glide.with(context)
                .load(drawableStatusResourced)
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(binding.statusImageView);


        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DeviceDetailActivity.class);
            intent.putExtra("deviceId", devices.get(position));
            context.startActivity(intent);
        });


        // Set OnClickListener for statusImageView
        holder.viewholderListBinding.statusImageView.setOnClickListener(view -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {

                Device clickedDevice = devices.get(adapterPosition);
                // Toggle the status of the clicked device
                deviceRepository.updateDeviceStatus(clickedDevice.getDeviceId(), clickedDevice.toggleStatus(),  new FirebaseUpdateDeviceCallback() {
                    @Override
                    public void onDeviceStatusUpdateSuccess() {
                        // Update the status icon
                        int newStatusDrawableResourceId = context.getResources().getIdentifier(DeviceUiUtils.generateStatusIcon(clickedDevice.getDeviceStatus()),
                                "drawable", context.getPackageName());
                        Glide.with(context)
                                .load(newStatusDrawableResourceId)
                                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                                .into(holder.viewholderListBinding.statusImageView);

                        Toast.makeText(context.getApplicationContext(), "Device status is " + clickedDevice.getDeviceStatus(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onDeviceStatusUpdateFailure(String errorMessage) {
                        Toast.makeText(context.getApplicationContext(), "Failed to update device status: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final DeviceViewholderListBinding viewholderListBinding;
        public ViewHolder(DeviceViewholderListBinding viewholderListBinding) {
            super(binding.getRoot());
            this.viewholderListBinding = viewholderListBinding;
        }
    }
}
