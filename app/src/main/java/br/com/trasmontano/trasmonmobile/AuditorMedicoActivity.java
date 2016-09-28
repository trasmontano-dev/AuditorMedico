package br.com.trasmontano.trasmonmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.trasmontano.trasmonmobile.domain.model.Associado;
import br.com.trasmontano.trasmonmobile.domain.model.Foto;
import br.com.trasmontano.trasmonmobile.domain.network.APIClient;
import br.com.trasmontano.trasmonmobile.domain.util.Base64Util;
import br.com.trasmontano.trasmonmobile.domain.util.InternetUtil;
import dmax.dialog.SpotsDialog;
import mehdi.sakout.fancybuttons.FancyButton;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AuditorMedicoActivity extends AppCompatActivity {

    private String matricula, dependente;
    private Callback<Foto> callbackFotoAssociado;
    private Callback<Associado> callbackInformacaoAssociado;
    EditText txtMatricula;
    TextView txtNome, lblAssociado;
    ImageView imageView;
    Spinner spnDependentes;
    SpotsDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditor_medico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtMatricula = (EditText)findViewById(R.id.txtMatricula);
        txtNome = (TextView)findViewById(R.id.txtNome);
        lblAssociado = (TextView)findViewById(R.id.lblAssociado);
        imageView = (ImageView)findViewById(R.id.imageViewAssociado);
        spnDependentes = (Spinner)findViewById(R.id.spnDependentes);
        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);

        lblAssociado.setText("");
        txtNome.setText("");

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //((InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromInputMethod(txtMatricula.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        configureFotoCallback();
        configureInformacaoAssociadoCallback();

        carregaCodigosDependentes();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtMatricula.getText().toString().equals("")){
                    Intent intent = new Intent(AuditorMedicoActivity.this, AssociadoActivity.class);
                    intent.putExtra("MATRICULA", matricula);
                    intent.putExtra("DEPENDENTE", dependente);
                    intent.putExtra("NOME_ASSOCIADO", txtNome.getText());
                    startActivity(intent);
                } else{
                    Snackbar.make(view, "Informe uma matrícula!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        FloatingActionButton fabBack = (FloatingActionButton)findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        FloatingActionButton btnSearch = (FloatingActionButton)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                if(!InternetUtil.onLine(AuditorMedicoActivity.this)) {
                    Snackbar.make(view, "Sem conexão com internet ativa!", Snackbar.LENGTH_LONG).show();
                }
                else if(txtMatricula.getText().toString().equals("")) {
                    Snackbar.make(view, "Informe uma matrícula!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {

                    spotsDialog.show();
                    trataMatricula(txtMatricula.getText().toString());
                    new APIClient().getRestService().getFotoAssociado(matricula, dependente, callbackFotoAssociado);
                }
            }
        });

        if(savedInstanceState != null){
            txtNome.setText(savedInstanceState.getString("NOME_ASSOCIADO"));
            imageView.setImageBitmap(Base64Util.decodeBase64(savedInstanceState.getString("FOTO_ASSOCIADO")));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        BitmapDrawable bitmapDrawable = ((BitmapDrawable)imageView.getDrawable());
        Bitmap bitmap = bitmapDrawable.getBitmap();

        outState.putString("NOME_ASSOCIADO", txtNome.getText().toString());
        outState.putString("FOTO_ASSOCIADO", Base64Util.encodeTobase64(bitmap));
    }

    private void carregaCodigosDependentes() {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 15; i++) {
            list.add(i < 10 ? "0" + Integer.toString(i) : Integer.toString(i));
        }

        Spinner spinner = (Spinner)findViewById(R.id.spnDependentes);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_dependente, list);
        spinner.setAdapter(adapter);
    }

    private void trataMatricula(String matriculaAssociado) {
        //this.matricula = matriculaAssociado.substring(0, matriculaAssociado.length() - 2);
        //this.dependente = matriculaAssociado.substring(matriculaAssociado.length() - 2);
        this.matricula = matriculaAssociado;
        this.dependente = spnDependentes.getSelectedItem().toString();
    }

    private void configureFotoCallback() {
        callbackFotoAssociado = new Callback<Foto>() {
            @Override
            public void success(Foto foto, Response response) {
                if(foto == null) {

                    View parentLayout = findViewById(R.id.relativeLayout);
                    Snackbar.make(parentLayout, "Associado não encontrado!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    //imageView = (ImageView)findViewById(R.id.imageViewAssociado);
                    imageView.setImageBitmap(Base64Util.decodeBase64(foto.getImagem()));
                    //spotsDialog.dismiss();

                    new APIClient().getRestService().getInformacaoAssociado(matricula, dependente, callbackInformacaoAssociado);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                //Log.d("ERRO-------->", error.getMessage().toString());
                View parentLayout = findViewById(R.id.relativeLayout);
                Snackbar.make(parentLayout, "Associado não encontrado!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                spotsDialog.dismiss();
            }
        };
    }

    private void configureInformacaoAssociadoCallback(){
        callbackInformacaoAssociado = new Callback<Associado>() {
            @Override
            public void success(Associado associado, Response response) {
                lblAssociado.setText("ASSOCIADO");
                txtNome.setText(associado.getNome());

                spotsDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
            }
        };
    }
}