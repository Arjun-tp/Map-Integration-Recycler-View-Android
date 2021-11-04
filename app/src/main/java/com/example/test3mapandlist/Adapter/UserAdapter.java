
package com.example.test3mapandlist.Adapter;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.test3mapandlist.EntityClass.UserModel;
        import com.example.test3mapandlist.R;
        import com.example.test3mapandlist.UpdateActivity;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    List<UserModel> list;
    DeleteItemClickListener deleteItemClickListener;

    public UserAdapter(Context context, List<UserModel> list, DeleteItemClickListener deleteItemClickListener) {
        this.context = context;
        this.list = list;
        this.deleteItemClickListener = deleteItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false));
    }

    // Adapter

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.dob.setText(String.valueOf(list.get(position).getBirthday()));
        holder.country.setText(String.valueOf(list.get(position).getCountry()));

        Bitmap selectedImage = BitmapFactory.decodeByteArray(list.get(position).getUserImage(),0,list.get(position).getUserImage().length);
        holder.image.setImageBitmap(selectedImage);

        // Update
        holder.editDataBtn.setOnClickListener((view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("nameSent", list.get(position).getName());
            intent.putExtra("birthdaySent", list.get(position).getBirthday());
            intent.putExtra("countrySent", list.get(position).getCountry());
            intent.putExtra("genderSent", list.get(position).getGender());
            intent.putExtra("latSent", list.get(position).getLat());
            intent.putExtra("lngSent", list.get(position).getLng());
            intent.putExtra("imageSent", list.get(position).getUserImage());
            intent.putExtra("idSent", String.valueOf(list.get(position).getKey()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }));

        // Delete
        holder.deleteDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemClickListener.onItemDelete(position, list.get(position).getKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, country, dob;
        Button editDataBtn, deleteDataBtn;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editDataBtn = itemView.findViewById(R.id.btnEdit);
            deleteDataBtn = itemView.findViewById(R.id.btnDelete);
            name = itemView.findViewById(R.id.name);
            country = itemView.findViewById(R.id.country);
            dob = itemView.findViewById(R.id.dob);
            image = itemView.findViewById(R.id.userImage);
        }
    }


    public interface DeleteItemClickListener {
        void onItemDelete(int position, int id);
    }
}
