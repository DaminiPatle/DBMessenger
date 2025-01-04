//package com.example.dbmessenger;
//
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.LinearInterpolator;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class UserAdpter extends RecyclerView.Adapter<UserAdpter.viewholder> {
//    MainActivity mainActivity;
//    ArrayList<Users> usersArrayList;
//    public UserAdpter(MainActivity mainActivity, ArrayList<Users> usersArrayList) {
//        this.mainActivity=mainActivity;
//        this.usersArrayList=usersArrayList;
//    }
//
//    @NonNull
//    @Override
//    public UserAdpter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);
//        return new viewholder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserAdpter.viewholder holder, int position) {
//
//        Users users =usersArrayList.get(position);
//        holder.username.setText(users.userName);
//        holder.userstatus.setText(users.status);
//        Picasso.get().load(users.profilepic).into(holder.userimg);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mainActivity, chatWin.class);
//                intent.putExtra("nameeee",users.getUserName());
//                intent.putExtra("reciverImg",users.getProfilepic());
//                intent.putExtra("uid",users.getUSerId());
//                mainActivity.startActivity(intent);
//            }
//        });
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//
//        return usersArrayList.size();
//    }
//
//    public class viewholder extends RecyclerView.ViewHolder {
//        CircleImageView userimg;
//        TextView username;
//        TextView userstatus;
//        public viewholder(@NonNull View itemView) {
//            super(itemView);
//            userimg =itemView.findViewById(R.id.userimg);
//            username =itemView.findViewById(R.id.username);
//            userstatus =itemView.findViewById(R.id.userstatus);
//
//        }
//    }
//}


package com.example.dbmessenger;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final MainActivity mainActivity;
    private final ArrayList<Users> usersArrayList;

    public UserAdapter(MainActivity mainActivity, ArrayList<Users> usersArrayList) {
        this.mainActivity = mainActivity;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = usersArrayList.get(position);
        holder.usernameTextView.setText(user.getUserName());
        holder.statusTextView.setText(user.getStatus());
        Picasso.get().load(user.getProfilepic()).into(holder.profileImageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, chatWin.class);
            intent.putExtra("nameeee", user.getUserName());
            intent.putExtra("reciverImg", user.getProfilepic());
            intent.putExtra("uid", user.getUSerId());
            mainActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImageView;
        TextView usernameTextView, statusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.userimg); // Correct ID
            usernameTextView = itemView.findViewById(R.id.username); // Correct ID
            statusTextView = itemView.findViewById(R.id.userstatus); // Correct ID
        }
    }
}

