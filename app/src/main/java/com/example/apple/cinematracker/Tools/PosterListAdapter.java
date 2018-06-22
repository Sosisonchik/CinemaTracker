package com.example.apple.cinematracker.Tools;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apple.cinematracker.Pojo.Poster;
import com.example.apple.cinematracker.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PosterListAdapter extends RecyclerView.Adapter<PosterListAdapter.PosterHolder> {
    private static List<Poster> posterList = new LinkedList<>();

    public void add(Poster newPoster){
        posterList.add(newPoster);
        notifyDataSetChanged();
    }

    public void clear(){
        posterList.clear();
    }

    @NonNull
    @Override
    public PosterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,null);
        return new PosterHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterHolder posterHolder, int i) {
        Poster currentPoster = posterList.get(i);
        posterHolder.bind(currentPoster);
    }

    @Override
    public int getItemCount() {
        return posterList.size();
    }

    public class PosterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.poster_image)
        RoundedImageView image;

        @BindView(R.id.poster_title)
        TextView title;

        @BindView(R.id.poster_time_list)
        ListView timeList;

        private void bind(Poster poster){
            title.setText(poster.getTitle());

            Picasso.get().load(poster.getImgUrl()).into(image);

            ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(itemView.getContext(),R.layout.time_item,poster.getTime());
            timeList.setAdapter(timeAdapter);

            itemView.setAnimation(AnimationUtils.loadAnimation(itemView.getContext(),R.anim.show_item));
        }

        public PosterHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
