package br.com.trasmontano.trasmonmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.trasmontano.trasmonmobile.domain.model.Associado;
import br.com.trasmontano.trasmonmobile.domain.network.APIClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AssociadoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String matricula, dependente;
    private Callback<Associado> callbackInformacaoAssociado;
    TextView txtMatricula, txtNome, txtNascimento, txtCns, txtRegiao, txtCategoria, txtSituacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        this.matricula = intent.getStringExtra("MATRICULA");
        this.dependente = intent.getStringExtra("DEPENDENTE");

        txtMatricula = (TextView)findViewById(R.id.txtMatricula);
        txtNome = (TextView)findViewById(R.id.txtNome);
        txtNascimento = (TextView)findViewById(R.id.txtNascimento);
        txtCns = (TextView)findViewById(R.id.txtCns);
        txtRegiao = (TextView)findViewById(R.id.txtRegiao);
        txtCategoria = (TextView)findViewById(R.id.txtCategoria);
        txtSituacao = (TextView)findViewById(R.id.txtSituacao);

        txtMatricula.setText(this.matricula + "-" + this.dependente);

        configureInformacaoAssociadoCallback();

        new APIClient().getRestService().getInformacaoAssociado(matricula, dependente, callbackInformacaoAssociado);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabBack);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.associado, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_associado_detalhe) {
            callIntent(AssociadoDetalheActivity.class);
        } else if (id == R.id.nav_ultimas_consultas) {
            callIntent(AssociadoUltimasConsultasActivity.class);
        } else if(id == R.id.nav_carencias) {
            callIntent(AssociadoCarenciasActivity.class);
        } else if(id == R.id.nav_entrevista) {
            callIntent(AssociadoEntrevistaQualificadaActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //----------------
    private void callIntent(Class target) {
        Intent intent = new Intent(AssociadoActivity.this, target);
        intent.putExtra("MATRICULA", matricula);
        intent.putExtra("DEPENDENTE", dependente);
        intent.putExtra("NOME_ASSOCIADO", txtNome.getText());
        startActivity(intent);
    }

    private void configureInformacaoAssociadoCallback(){
        callbackInformacaoAssociado = new Callback<Associado>() {
            @Override
            public void success(Associado associado, Response response) {
                txtNome.setText(associado.getNome());
                txtCns.setText(associado.getCartaoNacionalSaude());
                txtRegiao.setText(associado.getRegiao());
                txtCategoria.setText(associado.getCategoria().toUpperCase());
                txtSituacao.setText(associado.getSituacao().toUpperCase());

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String input = associado.getDataNascimento().substring(0, 10);
                Date date;
                try {
                    date = dateFormat.parse(input);
                    input = new SimpleDateFormat("dd/MM/yyyy").format(date);
                    txtNascimento.setText(input);
                } catch (ParseException ex){
                    Log.d("ERRO-------->", ex.getMessage().toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
            }
        };
    }
}