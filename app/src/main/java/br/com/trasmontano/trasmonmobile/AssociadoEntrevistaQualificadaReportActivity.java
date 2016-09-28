package br.com.trasmontano.trasmonmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.text.SimpleDateFormat;

import br.com.trasmontano.trasmonmobile.domain.adapter.CustomArrayAdapterEntrevistaQualificadaCIDs;
import br.com.trasmontano.trasmonmobile.domain.adapter.CustomArrayAdapterEntrevistaQualificadaQuestoes;
import br.com.trasmontano.trasmonmobile.domain.model.EntrevistaQualificada;
import br.com.trasmontano.trasmonmobile.domain.network.APIClient;
import br.com.trasmontano.trasmonmobile.domain.util.FormatUtil;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AssociadoEntrevistaQualificadaReportActivity extends AppCompatActivity {

    private String matricula, dependente, nomeAssociado;
    private int idEntrevista;
    private TextView lblAssociado, txtAssociado, txtID, txtCPF, txtRG, txtIdade, txtPeso, txtAltura,
            txtIMC, txtProposta, txtAdmissao, txtRealizacao;
    private ListView listViewEntrevistaQualificadaQuestoes, listViewEntrevistaQualificadaCIDs;
    private Callback<EntrevistaQualificada> callbackEntrevistaQualificada;
    private SpotsDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associado_entrevista_qualificada_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lblAssociado = (TextView)findViewById(R.id.lblAssociado);
        txtAssociado = (TextView)findViewById(R.id.txtAssociado);
        txtID = (TextView)findViewById(R.id.txtID);
        txtCPF = (TextView)findViewById(R.id.txtCPF);
        txtRG = (TextView)findViewById(R.id.txtRG);
        txtIdade = (TextView)findViewById(R.id.txtIdade);
        txtPeso = (TextView)findViewById(R.id.txtPeso);
        txtAltura = (TextView)findViewById(R.id.txtAltura);
        txtIMC = (TextView)findViewById(R.id.txtIMC);
        txtProposta = (TextView)findViewById(R.id.txtProposta);
        txtAdmissao = (TextView)findViewById(R.id.txtAdmissao);
        txtRealizacao = (TextView)findViewById(R.id.txtRealizacao);
        listViewEntrevistaQualificadaQuestoes = (ListView)findViewById(R.id.listViewEntrevistaQualificadaQuestoes);
        listViewEntrevistaQualificadaCIDs = (ListView)findViewById(R.id.listViewEntrevistaQualificadaCIDs);

        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);
        spotsDialog.show();

        Intent intent = getIntent();
        this.matricula = intent.getStringExtra("MATRICULA");
        this.dependente = intent.getStringExtra("DEPENDENTE");
        this.nomeAssociado = intent.getStringExtra("NOME_ASSOCIADO");
        this.idEntrevista = intent.getIntExtra("ID_ENTREVISTA", 0);

        lblAssociado.setText("ASSOCIADO (" + this.matricula + "-" + this.dependente + ")");
        txtAssociado.setText(this.nomeAssociado);
        txtID.setText(Integer.toString(this.idEntrevista));

        configureEntrevistaQualificadaCallback();
        new APIClient().getRestService().getEntrevistaQualificada(this.idEntrevista, callbackEntrevistaQualificada);

        FloatingActionButton fabBack = (FloatingActionButton) findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton fabSwap = (FloatingActionButton) findViewById(R.id.fabFlipper);
        fabSwap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right_left);
                //overridePendingTransition(R.anim.slide_left_right, R.anim.slide_right_left);

                ViewFlipper viewFlipper = (ViewFlipper)findViewById(R.id.myViewFlipper);
                //viewFlipper.startAnimation(animation);
                viewFlipper.setInAnimation(getApplicationContext(), R.anim.slide_left_right);
                viewFlipper.setOutAnimation(getApplicationContext(), R.anim.slide_right_left);
                viewFlipper.showNext();
            }
        });
    }

    private void configureEntrevistaQualificadaCallback() {
        callbackEntrevistaQualificada = new Callback<EntrevistaQualificada>() {
            @Override
            public void success(EntrevistaQualificada entrevistaQualificada, Response response) {
                setEntrevista(entrevistaQualificada);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
            }
        };
    }

    private void setEntrevista(EntrevistaQualificada entrevistaQualificada)
    {
        //ENTREVISTA
        txtCPF.setText(FormatUtil.formatCPF(entrevistaQualificada.getCPF()));
        txtRG.setText(entrevistaQualificada.getRG());
        txtIdade.setText(entrevistaQualificada.getIdade().toString());
        txtPeso.setText(entrevistaQualificada.getPeso().toString());
        txtAltura.setText(entrevistaQualificada.getAltura().toString());
        txtIMC.setText(entrevistaQualificada.getIMC().toString());
        txtProposta.setText(entrevistaQualificada.getProposta());

        txtAdmissao.setText(FormatUtil.formatDate(entrevistaQualificada.getDataAdmissao()));
        txtRealizacao.setText(FormatUtil.formatDate(entrevistaQualificada.getDataRealizacao()));

        //QUESTOES
        CustomArrayAdapterEntrevistaQualificadaQuestoes adapterQuestoes = new CustomArrayAdapterEntrevistaQualificadaQuestoes(this,
                R.layout.list_item_entrevista_qualificada_questoes, entrevistaQualificada.getQuestoes());
        listViewEntrevistaQualificadaQuestoes.setAdapter(adapterQuestoes);

        //CIDs
        CustomArrayAdapterEntrevistaQualificadaCIDs adapterCIDs = new CustomArrayAdapterEntrevistaQualificadaCIDs(this,
                R.layout.list_item_entrevista_qualificada_cids, entrevistaQualificada.getCIDs());
        listViewEntrevistaQualificadaCIDs.setAdapter(adapterCIDs);

        spotsDialog.dismiss();
    }
}