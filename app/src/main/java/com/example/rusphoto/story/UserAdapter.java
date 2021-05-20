package com.example.rusphoto.story;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rusphoto.databinding.UserRowBinding;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    public List<User> texts;
    public UserAdapter(List<User> texts) {
        this.texts = texts;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserRowBinding binding = UserRowBinding.inflate(LayoutInflater.from
                (parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        holder.binding.firstName.setText(texts.get(position).getName());
        holder.binding.lastName.setText(texts.get(position).getLastname());
        holder.binding.email.setText(texts.get(position).getEmail());
//        holder.binding.buttonDelete.setOnClickListener(v -> {
//            Log.d("text", String.valueOf(position));
//        });
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        UserRowBinding binding;

        public ViewHolder(UserRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
