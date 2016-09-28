package br.com.trasmontano.trasmonmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.trasmontano.trasmonmobile.domain.adapter.CustomArrayAdapter;
import br.com.trasmontano.trasmonmobile.domain.model.ItemCarencia;
import br.com.trasmontano.trasmonmobile.domain.network.APIClient;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AssociadoCarenciasActivity extends AppCompatActivity {

    private String matricula, dependente;
    TextView txtNome, lblNome;
    ListView listViewCarencias;
    private Callback<List<ItemCarencia>> callbackCarenciasAssociado;
    List<ItemCarencia> list;
    SpotsDialog spotsDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associado_carencias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNome = (TextView)findViewById(R.id.txtNome);
        lblNome = (TextView)findViewById(R.id.lblNome);
        listViewCarencias = (ListView)findViewById(R.id.listViewCarencias);
        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);

        Intent intent = getIntent();
        this.matricula = intent.getStringExtra("MATRICULA");
        this.dependente = intent.getStringExtra("DEPENDENTE");
        txtNome.setText(intent.getStringExtra("NOME_ASSOCIADO"));
        lblNome.setText("ASSOCIADO (" + this.matricula + "-" + this.dependente + ")");

        configureCarenciasAssociadoCallback();

        spotsDialog.show();

        new APIClient().getRestService().getCarencias(this.matricula, this.dependente, this.callbackCarenciasAssociado);

        FloatingActionButton fabBack = (FloatingActionButton) findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void configureCarenciasAssociadoCallback(){
        callbackCarenciasAssociado = new Callback<List<ItemCarencia>>() {
            @Override
            public void success(List<ItemCarencia> items, Response response) {

                list = new ArrayList<>();

                for(ItemCarencia item:items){
                    list.add(item);
                }

                carregaListView(list);

                spotsDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                spotsDialog.dismiss();
            }
        };
    }

    private void carregaListView(List<ItemCarencia> list){
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, R.layout.list_item_carencia, list);
        listViewCarencias.setAdapter(adapter);
    }
}
