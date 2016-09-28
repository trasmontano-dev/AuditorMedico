package br.com.trasmontano.trasmonmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import br.com.trasmontano.trasmonmobile.domain.adapter.CustomArrayAdapter;
import br.com.trasmontano.trasmonmobile.domain.model.Associado;
import br.com.trasmontano.trasmonmobile.domain.model.ItemCarencia;
import br.com.trasmontano.trasmonmobile.domain.network.APIClient;
import br.com.trasmontano.trasmonmobile.domain.util.FormatUtil;
import br.com.trasmontano.trasmonmobile.domain.util.InternetUtil;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AssociadoDetalheActivity extends AppCompatActivity {

    private String matricula, dependente;
    private Callback<Associado> callbackInformacaoAssociado;
    TextView txtMatricula, txtNome, txtNascimento, txtCns, txtRegiao, txtSituacao;
    SpotsDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associado_detalhe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtMatricula = (TextView)findViewById(R.id.txtMatricula);
        txtNome = (TextView)findViewById(R.id.txtNome);
        txtNascimento = (TextView)findViewById(R.id.txtNascimento);
        txtCns = (TextView)findViewById(R.id.txtCns);
        txtRegiao = (TextView)findViewById(R.id.txtRegiao);
        txtSituacao = (TextView)findViewById(R.id.txtSituacao);

        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);

        Intent intent = getIntent();
        this.matricula = intent.getStringExtra("MATRICULA");
        this.dependente = intent.getStringExtra("DEPENDENTE");

        TextView txtMatricula = (TextView)findViewById(R.id.txtMatricula);
        txtMatricula.setText(this.matricula + "-" + this.dependente);

        configureInformacaoAssociadoCallback();

        spotsDialog.show();

        new APIClient().getRestService().getInformacaoAssociado(matricula, dependente, callbackInformacaoAssociado);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void configureInformacaoAssociadoCallback(){
        callbackInformacaoAssociado = new Callback<Associado>() {
            @Override
            public void success(Associado associado, Response response) {
                txtNome.setText(associado.getNome());
                txtCns.setText(associado.getCartaoNacionalSaude());
                txtRegiao.setText(associado.getRegiao());
                txtSituacao.setText(associado.getSituacao().toUpperCase());
                txtNascimento.setText(FormatUtil.formatDate(associado.getDataNascimento()));

                spotsDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
            }
        };
    }
}