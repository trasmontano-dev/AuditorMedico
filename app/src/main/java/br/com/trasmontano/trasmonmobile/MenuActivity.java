package br.com.trasmontano.trasmonmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.trasmontano.trasmonmobile.domain.util.Base64Util;
import br.com.trasmontano.trasmonmobile.domain.util.FormatUtil;

public class MenuActivity extends AppCompatActivity {

    TextView txtImei, txtNome, txtLogin, txtDepartamento, txtData;
    ImageView imageViewLogo;
    CircularImageView circularImageView;
    Date data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        data = new Date();
        txtImei = (TextView)findViewById(R.id.txtImei);
        txtNome = (TextView)findViewById(R.id.txtNome);
        txtLogin = (TextView)findViewById(R.id.txtLogin);
        txtDepartamento = (TextView)findViewById(R.id.txtDepartamento);
        txtData = (TextView)findViewById(R.id.txtData);
        circularImageView = (CircularImageView)findViewById(R.id.imageViewUser);
        imageViewLogo = (ImageView)findViewById(R.id.imageViewLogo);

        txtData.setText(dateFormat.format(data));

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        imageViewLogo.startAnimation(animation);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fabAuditorMedico = (FloatingActionButton)findViewById(R.id.fabAuditorMedico);
        fabAuditorMedico.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MenuActivity.this, AuditorMedicoActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fabSignOut = (FloatingActionButton)findViewById(R.id.fabSignOut);
        fabSignOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        TelephonyManager manager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        SharedPreferences settings = getSharedPreferences("SecuritySettings", Activity.MODE_PRIVATE);

        //txtImei.setText(manager.getDeviceId().toString());
        txtImei.setText("987987987");
        txtNome.setText(settings.getString("USUARIO_NOME", "").toUpperCase());
        txtLogin.setText(settings.getString("USUARIO_LOGIN", "").toUpperCase());
        txtDepartamento.setText(settings.getString("USUARIO_DEPARTAMENTO", ""));

        //String f = settings.getString("USUARIO_FOTO", "");
        //circularImageView.setImageBitmap(Base64Util.decodeBase64(settings.getString("USUARIO_FOTO", "")));
        Intent intent = getIntent();
        circularImageView.setImageBitmap(Base64Util.decodeBase64(intent.getStringExtra("USUARIO_FOTO")));
    }
}