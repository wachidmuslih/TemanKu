package com.example.temanku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.temanku.adapter.TemanAdapter;
import com.example.temanku.database.DBController;
import com.example.temanku.database.Teman;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //deklarasi
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private TemanAdapter adapter;
    //untuk menyimpan data teman
    private ArrayList<Teman> temanArrayList;

//    DBController controller = new DBController(this);
//    String id,nm,tlp;

//    private Button btn;

    private static  final String TAG = MainActivity.class.getSimpleName();
    private static String url_select = "http://10.0.2.2/umyTI/bacateman.php";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_TELPON = "telpon";

    /*public class MyViewHolder extends RecyclerView.ViewHolder{
        private Teman temanSelected;
        public TextView nama, telepon;
        public DBController DB;

        public MyViewHolder(View view, DBController db) {
            this(view);
            this.DB = db;
        }

        public MyViewHolder(@NonNull View view) {
            super(view);
            nama = view.findViewById(R.id.edNama);
            telepon = view.findViewById(R.id.edTelpon);
        }

        private void ViewDetail(View view) {
            Teman kontakItem = temanArrayList.get(getAdapterPosition());
            Intent i = new Intent(getApplicationContext(), MenampilkanData.class);
            Bundle b = new Bundle();
            b.putString("nama", kontakItem.getNama());
            b.putString("nomor", kontakItem.getTelpon());
            b.putBoolean("edit", false);
            i.putExtras(b);
            startActivity(i);
        }
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.floatingBtn);
        BacaData();
        adapter = new TemanAdapter(temanArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahTeman.class);
                startActivity(intent);
            }
        });
        //btn = findViewById(R.id.bHapus);

        /*btn.setOnClickListener((v ->{
            new AlertDialog.Builder(this).setMessage("Hapus semuanya?")
                    .setPositiveButton("Hapus",(y, x)-> {
                        Toast.makeText(this, "Hapus", Toast.LENGTH_SHORT).show();
                    } )
                    .setNegativeButton("Batal",(y, x)-> {
                        Toast.makeText(this, "Batal", Toast.LENGTH_SHORT).show();
                    }).show();
        }));*/

        //BacaData();
        //adapter = new TemanAdapter(temanArrayList, this);


//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,TemanBaru.class);
//                startActivity(intent);
//            }
//        });

    }

    public void BacaData() {
        temanArrayList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jArr = new JsonArrayRequest(url_select, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());

                //parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        Teman item = new Teman();

                        item.setId(obj.getString(TAG_ID));
                        item.setNama(obj.getString(TAG_NAMA));
                        item.setTelpon(obj.getString(TAG_TELPON));

                        //menambah item ke array
                        temanArrayList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: "+ error.getMessage());
                error.printStackTrace();
                Toast.makeText(MainActivity.this,"gagal", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jArr);
    }




    /*@Override
    public void onTemanSelected(View v, Teman teman, int pos) {
        showPopup(v);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.edit:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent = new Intent(getApplicationContext(),MengeditData.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.hapus:
                Toast.makeText(getApplicationContext(),"Ini untuk delete kontak",
                        Toast.LENGTH_LONG).show();
                break;
        }
        return false;
    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popupmenu, popup.getMenu());
        popup.show();
    }*/
}