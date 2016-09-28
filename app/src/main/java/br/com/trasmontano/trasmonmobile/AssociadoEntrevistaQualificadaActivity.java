package br.com.trasmontano.trasmonmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.w3c.dom.Text;

import java.util.List;

import br.com.trasmontano.trasmonmobile.domain.adapter.CustomArrayAdapterEntrevistaQualificadaId;
import br.com.trasmontano.trasmonmobile.domain.model.Associado;
import br.com.trasmontano.trasmonmobile.domain.model.EntrevistaQualificada;
import br.com.trasmontano.trasmonmobile.domain.network.APIClient;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AssociadoEntrevistaQualificadaActivity extends AppCompatActivity {

    private String matricula, dependente;
    private Callback<List<Integer>> callbackEntrevistaQualificadaIds;
    private CustomArrayAdapterEntrevistaQualificadaId adapter;
    private SpotsDialog spotsDialog;
    TextView txtAssociado, lblAssociado;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associado_entrevista_qualificada);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtAssociado = (TextView)findViewById(R.id.txtAssociado);
        lblAssociado = (TextView)findViewById(R.id.lblAssociado);
        listView = (ListView)findViewById(R.id.listViewEntrevistaQualificada);

        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);
        spotsDialog.show();

        final Intent intent = getIntent();
        this.matricula = intent.getStringExtra("MATRICULA");
        this.dependente = intent.getStringExtra("DEPENDENTE");

        this.txtAssociado.setText(intent.getStringExtra("NOME_ASSOCIADO"));
        this.lblAssociado.setText("ASSOCIADO (" + this.matricula + "-" + this.dependente + ")");

        configureEntrevistaQualificadaIdsCallback();
        new APIClient().getRestService().getEntrevistaQualificadaIds(this.matricula, this.dependente, callbackEntrevistaQualificadaIds);

        FloatingActionButton fabBack = (FloatingActionButton) findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intentReport = new Intent(AssociadoEntrevistaQualificadaActivity.this,
                        AssociadoEntrevistaQualificadaReportActivity.class);

                intentReport.putExtra("ID_ENTREVISTA", Integer.parseInt(((TextView)view.findViewById(R.id.txtId)).getText().toString()));
                intentReport.putExtra("MATRICULA", matricula);
                intentReport.putExtra("DEPENDENTE", dependente);
                intentReport.putExtra("NOME_ASSOCIADO", txtAssociado.getText());

                startActivity(intentReport);
            }
        });
    }

    private void configureEntrevistaQualificadaIdsCallback() {
        callbackEntrevistaQualificadaIds = new Callback<List<Integer>>() {
            @Override
            public void success(List<Integer> entrevistaQualificadaIds, Response response) {
                carregaListView(entrevistaQualificadaIds);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
            }
        };
    }

    private void carregaListView(List<Integer> list) {
        adapter = new CustomArrayAdapterEntrevistaQualificadaId(this,
                R.layout.list_item_entrevista_qualificada_id, list);

        listView.setAdapter(adapter);

        spotsDialog.dismiss();
    }
}