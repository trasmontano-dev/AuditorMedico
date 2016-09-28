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
import br.com.trasmontano.trasmonmobile.domain.adapter.CustomArrayAdapterUltimasConsultas;
import br.com.trasmontano.trasmonmobile.domain.model.ItemCarencia;
import br.com.trasmontano.trasmonmobile.domain.model.UltimasConsultas;
import br.com.trasmontano.trasmonmobile.domain.network.APIClient;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AssociadoUltimasConsultasActivity extends AppCompatActivity {

    private String matricula, dependente;
    ListView listViewUltimasConsultas;
    TextView txtNome, lblNome;
    private Callback<List<UltimasConsultas>> callbackUltimasConsultasAssociado;
    List<UltimasConsultas> list;
    SpotsDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associado_ultimas_consultas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtNome = (TextView)findViewById(R.id.txtNome);
        lblNome = (TextView)findViewById(R.id.lblNome);
        listViewUltimasConsultas = (ListView)findViewById(R.id.listViewUltimasConsultas);
        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);

        Intent intent = getIntent();
        this.matricula = intent.getStringExtra("MATRICULA");
        this.dependente = intent.getStringExtra("DEPENDENTE");
        txtNome.setText(intent.getStringExtra("NOME_ASSOCIADO"));
        lblNome.setText("ASSOCIADO (" + this.matricula + "-" + this.dependente + ")");

        configureUltimasConsultasAssociadoCallback();

        spotsDialog.show();

        new APIClient().getRestService().getUltimasConsultas(this.matricula, this.dependente,
                this.callbackUltimasConsultasAssociado);

        FloatingActionButton fabBack = (FloatingActionButton) findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void configureUltimasConsultasAssociadoCallback() {
        callbackUltimasConsultasAssociado = new Callback<List<UltimasConsultas>>() {
            @Override
            public void success(List<UltimasConsultas> items, Response response) {

                list = new ArrayList<>();

                for(UltimasConsultas item:items){
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

    private void carregaListView(List<UltimasConsultas> list){
        CustomArrayAdapterUltimasConsultas adapter = new CustomArrayAdapterUltimasConsultas(this,
                R.layout.list_item_ultimas_consultas, list);
        listViewUltimasConsultas.setAdapter(adapter);
    }

}