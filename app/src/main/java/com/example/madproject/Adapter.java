package com.example.madproject;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    public LayoutInflater layoutInflater;
    public List<User> data;
    public Adapter(Context context, List<User> data){
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }
    public class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView name;
        public TextView profession;
        public TextView descripion;
        public  ImageView profilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.card_name);
            profession = itemView.findViewById(R.id.card_profession);
            descripion = itemView.findViewById(R.id.card_description);
            profilePic = (ImageView) itemView.findViewById(R.id.result_pic);

        }
    }
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.custom_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String name = data.get(position).getName();
        String profession = data.get(position).getProfession();
        String description = data.get(position).getDescription();
        holder.name.setText(name);
        holder.profession.setText(profession);
        holder.descripion.setText(description);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User u = DatabaseHandler.getUserByName(name);

                Intent intent = new Intent(holder.itemView.getContext(),CardProfileActivity.class);
                System.out.println("User data:"+u);
                intent.putExtra("name",u.getName());
                intent.putExtra("email",u.getEmail());
                intent.putExtra("experience",u.getExperience());
                intent.putExtra("phone",u.getPhone());
                intent.putExtra("description",u.getDescription());
                intent.putExtra("image","gs://madproject-ee5db.appspot.com/images/"+u.getName());
                intent.putExtra("city",u.getCity());
                intent.putExtra("profession",u.getProfession());
                holder.itemView.getContext().startActivity(intent);

            }
        });


        Glide.with(layoutInflater.getContext()).load(FirebaseStorage.getInstance().getReferenceFromUrl("gs://madproject-ee5db.appspot.com/images/"+name)).into(holder.profilePic);



    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
