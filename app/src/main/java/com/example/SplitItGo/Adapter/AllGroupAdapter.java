package com.example.SplitItGo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.SplitItGo.Model.GroupResponse;
import com.example.SplitItGo.R;
import java.util.ArrayList;

public class AllGroupAdapter extends RecyclerView.Adapter<AllGroupAdapter.ModelViewHolder> {

        Context context;
        ArrayList<GroupResponse> groupList;
        OnItemClickListener mListener;

        public AllGroupAdapter(Context context, ArrayList<GroupResponse> groupItemArrayList) {
            this.context = context;
            groupList = groupItemArrayList;
        }

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }

        @NonNull
        @Override
        public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);
                return new ModelViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {
                GroupResponse groupItem = groupList.get(position);
                String username = groupItem.getName();
                holder.textView.setText(username);
        }

        @Override
        public int getItemCount() {
                return groupList.size();
        }

    public class ModelViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;

        public ModelViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.imageViewCardView);
            textView = view.findViewById(R.id.textViewCardView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
