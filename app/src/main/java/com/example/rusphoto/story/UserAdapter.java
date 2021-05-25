package com.example.rusphoto.story;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rusphoto.databinding.UserRowBinding;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    public List<User> users;
    private OnDeleteTextListener deleteTextListener;

    public UserAdapter(List<User> texts, OnDeleteTextListener deleteTextListener) {
        this.users = texts;
        this.deleteTextListener = deleteTextListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserRowBinding binding = UserRowBinding.inflate(LayoutInflater.from
                (parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        holder.binding.textView.setText(users.get(position).getName());
        holder.binding.buttonDelete.setOnClickListener(v -> {
            Log.d("text", String.valueOf(position));
            deleteTextListener.onDelete(position);
        });
    }

    public interface OnDeleteTextListener{
        void onDelete(int position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        UserRowBinding binding;

        public ViewHolder(UserRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
