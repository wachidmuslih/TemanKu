package com.example.temanku.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.temanku.MainActivity;
import com.example.temanku.R;
import com.example.temanku.app.AppController;
import com.example.temanku.database.Teman;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> implements Filterable {

    private ArrayList<Teman> listdata;
    //private final TemanAdapterListener listener;

    public TemanAdapter(ArrayList<Teman> listdata) {
        this.listdata = listdata;
    }

//    public interface TemanAdapterListener {
//        void onTemanSelected(View v, Teman teman, int pos);
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder
//    {
//        private Teman Teman;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//
//    //construktor
//    public TemanAdapter(ArrayList<Teman> listdata, TemanAdapterListener listener) {
//        this.listdata = listdata;
//        //this.listener = listener;
//    }

    //1 memanggil tampilan/layout dari adapternya  menggunakan Inflater
    @NonNull
    @Override
    public TemanViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.row_data_teman,parent,false);
        return new TemanViewHolder(view);

    }

    //3 untuk menampilkan
    @Override
    public void onBindViewHolder(@NonNull TemanViewHolder holder, int position) {
        String nm,tlp, id;

        id = listdata.get(position).getId();
        nm = listdata.get(position).getNama();
        tlp = listdata.get(position).getTelpon();

        holder.namaTxt.setTextColor(Color.BLUE);
        holder.namaTxt.setTextSize(20);
        holder.namaTxt.setText(nm);
        holder.telponTxt.setText(tlp);

        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu pm = new PopupMenu(v.getContext(), v);
                pm.inflate(R.menu.popup1);

                pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.edit:
                                Bundle bendel = new Bundle();

                                bendel.putString("kunci1", id);
                                bendel.putString("kunci2", nm);
                                bendel.putString("kunci3", tlp);

                                Intent inten = new Intent();
                                inten.putExtras(bendel);
                                v.getContext().startActivity(inten);
                                break;

                            case R.id.hapus:
                                AlertDialog.Builder alertdb = new AlertDialog.Builder(v.getContext());
                                alertdb.setTitle("Yakin " +nm+" akan dihapus?");
                                alertdb. setMessage("Tekan Ya untuk menghapus");
                                alertdb. setCancelable(false);
                                alertdb. setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        HapusData(id);
                                        Toast.makeText(v.getContext(), "Data "+id+" telah dihapus", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(v. getContext(), MainActivity.class);
                                        v.getContext().startActivity(intent);
                                    }
                                });
                                alertdb.setNegativeButton("Tidak", new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog adlg = alertdb.create();
                                adlg.show();
                                break;
                        }
                        return true;
                    }
                });
                pm.show();
                return true;
            }
        });
    }

    private void HapusData(final String idx) {
        String url_update = "http://10.0.2.2/umyTI/deletetm.php";
        final String TAG = MainActivity.class.getSimpleName();
        final String TAG_SUCCES = "success";
        final int[] sukses = new int[1];

        StringRequest stringReq = new StringRequest(Request. Method. POST, url_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"Respon: " + response.toString());
                try{
                    JSONObject jobj = new JSONObject(response);
                    sukses [0] = jobj.getInt(TAG_SUCCES) ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log . e (TAG, "Error : "+ error. getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams()  {
                Map<String , String> params = new HashMap<>();
                params.put("id", idx);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringReq);

    }

    //4 menghitung ukuran dari arraylist
    // bisa ditambahin / biarkan saja
    @Override
    public int getItemCount() {
        return (listdata != null)?listdata.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    // untuk mendaftarkan terlebih dahulu
    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardku;
        private TextView namaTxt,telponTxt;
        public TemanViewHolder(View view) {
            super(view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            namaTxt = (TextView) view.findViewById(R.id.textNama);
            telponTxt = (TextView) view.findViewById(R.id.textTelpon);

            /*cardku.setOnClickListener(v->{
                Teman KontakItem = listdata.get(getAdapterPosition());
                listener.onTemanSelected(v, KontakItem, getAdapterPosition());

            });*/
        }
    }



}
