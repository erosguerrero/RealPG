package com.example.realpg.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.realpg.R;

import java.util.ArrayList;
import java.util.List;

public class PokeListAdapter extends RecyclerView.Adapter<PokeListAdapter.PokeViewHolder> {

    Context context;
    private int firstIdSelected = RecyclerView.NO_POSITION;
    private int selectedPos = RecyclerView.NO_POSITION;

    public PokeListAdapter(Context context, int firstIdSelected)
    {
        this.context = context;
        this.firstIdSelected = firstIdSelected;
    }
    private ArrayList<PokeRecyclerInfo> pokeListData = new ArrayList<PokeRecyclerInfo>();
        TextView loadingText;

    public void setLoadingTextView(TextView t) {
        loadingText = t;
    }

    public void setPokeListData(List<PokeRecyclerInfo> data){
        pokeListData = (ArrayList<PokeRecyclerInfo>) data;
        int pos= 0;
        for(PokeRecyclerInfo p: pokeListData)
        {
            if(p.getIdPoke() == firstIdSelected)
            {
                selectedPos = pos;
                return;
            }
                pos++;
        }
    }

    @NonNull
    @Override
    public PokeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Crea la asociación entre el xml de datos (Layout principal) y el código.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poke_element, null, false);

        return new PokeViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull PokeViewHolder holder, int position) {
        //Comunicación entre esta clase y BookViewHolder (La interna de abajo)
       // holder.asignarDatos(pokeListData.get(position));
        holder.asignarDatos(pokeListData.get(position));
        holder.itemView.setSelected(selectedPos == position);
    }

    @Override
    public int getItemCount() {
        return pokeListData.size();
    }

    public void updatePokeList(List<PokeRecyclerInfo> pokeListData){
        setPokeListData(pokeListData);

        notifyDataSetChanged();
    }

    /*public void updateSelected(int idSelected, int previousSelected){
        PokeRecyclerInfo pokeSelect = null;

        //deselecciona al anterior si lo habia
        if(previousSelected != -1)
        {
            int pos = 0;
            for (PokeRecyclerInfo pokeInfo: pokeListData) {
                if(pokeInfo.idPoke == previousSelected)
                {
                    pokeInfo.isSelected = false;
                    break;
                }
                pos++;
            }

            notifyItemChanged(pos);
        }


        int pos = 0;
        for (PokeRecyclerInfo pokeInfo: pokeListData) {
            if(pokeInfo.idPoke == idSelected)
            {
                pokeInfo.isSelected = true;
                break;
            }
            pos++;
        }

        notifyItemChanged(pos);
        notifyDataSetChanged();






        notifyDataSetChanged();

        for (PokeRecyclerInfo pokeInfo: pokeListData) {
            if(pokeInfo.idPoke == idSelected)
                pokeSelect = pokeInfo;
        }

        pokeSelect.isSelected = true;
        notifyDataSetChanged();


     //   pokeListData.get(index).isSelected = true;

    }*/
    public void restart()
    {
        setPokeListData(new ArrayList<PokeRecyclerInfo>());
        notifyDataSetChanged();
    }

    public PokeRecyclerInfo getSelectedPoke()
    {
        return pokeListData.get(selectedPos);
    }

public class PokeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView textName;
    PokeRecyclerInfo pokeInfo;

   // View allLayout;

    Context context;
    ImageView pokeImg;

    ProgressBar progressBar;

    public PokeViewHolder(@NonNull View itemView, Context context) {
        super(itemView);

        this.context = context;
        itemView.setOnClickListener(this);

        textName = itemView.findViewById(R.id.pokeName);
        //allLayout = itemView;
        pokeImg = itemView.findViewById(R.id.pokeImg);

        progressBar = itemView.findViewById(R.id.pokeProgressBar);
    }

    public void asignarDatos(PokeRecyclerInfo pokeInfo){
        this.pokeInfo = pokeInfo;

     /*   if(pokeInfo.isSelected)
        {
            allLayout.setBackground(ContextCompat.getDrawable(allLayout.getContext(), R.drawable.custom_border));
        }
*/
        textName.setText(pokeInfo.name);


        if(pokeImg != null)
        {
            Log.i("demo", "en el if de url");
            Glide.with(context).load(pokeInfo.getImgUrlStr())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(pokeImg);
        }

    }

    @Override
    public void onClick(View v) {
       // ((ListMyPoke)context).updateSelected(pokeInfo.idPoke);
      //  allLayout.setBackground(ContextCompat.getDrawable(allLayout.getContext(), R.drawable.custom_border));
        //v.setBackground(ContextCompat.getDrawable(v.getContext(), R.drawable.custom_border));
        notifyItemChanged(selectedPos);
        selectedPos = getLayoutPosition();
        notifyItemChanged(selectedPos);
        Context context = v.getContext();

    }
}


}
