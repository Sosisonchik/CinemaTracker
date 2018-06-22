package com.example.apple.cinematracker;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apple.cinematracker.Pojo.Poster;
import com.example.apple.cinematracker.Tools.Efect;
import com.example.apple.cinematracker.Tools.PosterListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.posters_list)
    RecyclerView postersList;

    @BindView(R.id.update_button)
    FloatingActionButton updateButton;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    PosterListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        progressBar.setVisibility(View.GONE);

        initRecycler();
        updateData();

    }


    @OnClick(R.id.update_button)
    public void update(View view){
        updateData();
    }

    private void updateData(){
        new GetPosters().execute();
    }

    private void initRecycler(){
        adapter = new PosterListAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        postersList.setLayoutManager(layoutManager);
        postersList.setAdapter(adapter);

        postersList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0)
                    updateButton.hide();
                else
                    updateButton.show();

                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void onLoadingStart(){
        progressBar.setVisibility(View.VISIBLE);
        postersList.setVisibility(View.GONE);
    }

    private void onLoadingFinish(){
        progressBar.setVisibility(View.GONE);
        postersList.setVisibility(View.VISIBLE);
    }

    private class GetPosters extends AsyncTask<Void,Void,List<Poster>> {

        @Override
        protected void onPreExecute() {
            onLoadingStart();
            super.onPreExecute();
        }

        @Override
        protected List<Poster> doInBackground(Void... voids) {
            List<Poster> posters = Efect.getInstance().parse(); //Получаем List постеров с сайта кинотеатра

            if (adapter.getItemCount()!=0)
                adapter.clear();

            Log.d("TEST",posters.toString());

            return posters;
        }

        @Override
        protected void onPostExecute(List<Poster> posters) {
            super.onPostExecute(posters);
            onLoadingFinish();
            for (Poster poster : posters)
                adapter.add(poster);
        }
    }


}
