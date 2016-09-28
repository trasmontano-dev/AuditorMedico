package br.com.trasmontano.trasmonmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import br.com.trasmontano.trasmonmobile.domain.model.Associado;
import br.com.trasmontano.trasmonmobile.domain.model.Usuario;
import br.com.trasmontano.trasmonmobile.domain.network.APIClient;
import br.com.trasmontano.trasmonmobile.domain.util.InternetUtil;
import dmax.dialog.SpotsDialog;
import mehdi.sakout.fancybuttons.FancyButton;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    EditText editTextLogin, editTextPassword;
    Switch swicthLembrarMe;
    SpotsDialog spotsDialog;
    private Callback<Usuario> callbackUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView)findViewById(R.id.imageView);
        editTextLogin = (EditText)findViewById(R.id.editTextLogin);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        swicthLembrarMe = (Switch)findViewById(R.id.switchLembrarMe);

        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        imageView.startAnimation(animation);

        Drawable loginBackground = findViewById(R.id.relativeLayout).getBackground();
        loginBackground.setAlpha(100);

        configureInformacaoAssociadoCallback();

        SharedPreferences settings = getSharedPreferences("AuxSettings", Activity.MODE_PRIVATE);
        editTextLogin.setText(settings.getString("LOGIN", ""));
        swicthLembrarMe.setChecked(settings.getBoolean("LEMBRARME", false));

        FancyButton buttonLogin = (FancyButton) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (editTextLogin.getText().toString().equals("") || editTextPassword.getText().toString().equals(""))
                    Snackbar.make(view, "Informe seu login e senha!", Snackbar.LENGTH_LONG).show();
                else if (!InternetUtil.onLine(MainActivity.this))
                    Snackbar.make(view, "Sem conexão com internet ativa!", Snackbar.LENGTH_LONG).show();
                else {
                    spotsDialog.show();

                    lembrarMe();

                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromInputMethod(editTextPassword.getWindowToken(), 0);

                    new APIClient().getRestService().getUsuario(editTextLogin.getText().toString(),
                            editTextPassword.getText().toString(), callbackUsuario);
                }
            }
       });
    }

    private void lembrarMe(){

        SharedPreferences settings = getSharedPreferences("AuxSettings", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        if(swicthLembrarMe.isChecked()){
            editor.putString("LOGIN", editTextLogin.getText().toString());
            editor.putBoolean("LEMBRARME", true);
        } else {
            editor.putString("LOGIN", "");
            editor.putBoolean("LEMBRARME", false);
        }

        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void configureInformacaoAssociadoCallback(){
        callbackUsuario = new Callback<Usuario>() {
            @Override
            public void success(Usuario usuario, Response response) {
                if(usuario.getPermitido().toString().equals("true")){
                    SharedPreferences settings = getSharedPreferences("SecuritySettings", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("USUARIO_NOME", usuario.getNome());
                    editor.putString("USUARIO_LOGIN", usuario.getLogin());
                    editor.putString("USUARIO_CCUSTO", usuario.getCCusto());
                    editor.putString("USUARIO_DEPARTAMENTO", usuario.getDepartamento());
                    editor.commit();

                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("USUARIO_FOTO", usuario.getFoto());

                    spotsDialog.dismiss();

                    startActivity(intent);
                } else {
                    View parentLayout = findViewById(R.id.relativeLayout);
                    Snackbar.make(parentLayout, "Informe seu login e senha!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                spotsDialog.dismiss();
            }
        };
    }
}