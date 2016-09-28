package br.com.trasmontano.trasmonmobile;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.server.converter.StringToIntConverter;

import java.util.Properties;

//import javax.mail.Address;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.mail.Message.RecipientType;
import javax.mail.*;
import javax.mail.internet.*;

import br.com.trasmontano.trasmonmobile.domain.util.EmailAPI;

public class ContactActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        FloatingActionButton fabBack = (FloatingActionButton)findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"fmuniz@trasmontano.com.br"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "App Android - Auditor Médico");
                intent.putExtra(Intent.EXTRA_TEXT, editTextEmail.getText());

                try{
                    startActivity(Intent.createChooser(intent, "ENVIAR EMAIL..."));
                } catch (android.content.ActivityNotFoundException ex){
                    View parentLayout = findViewById(R.id.relativeLayout);
                    Snackbar.make(parentLayout, "Nenhum cliente de email instalado!", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        FloatingActionButton fabClearAll = (FloatingActionButton)findViewById(R.id.fabClearAll);
        fabClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextEmail.setText("");
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    protected void sendEmail(String nome, String emailTo) {

        //String[] recipients = {emailTo};
        //Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));

        //email.setType("message/rfc822");
        //email.putExtra(Intent.EXTRA_EMAIL, recipients);
        //email.putExtra(Intent.EXTRA_SUBJECT, "App Android Trasmontano");
        //email.putExtra(Intent.EXTRA_TEXT, "Primeiro email envido através do app android!!");

        //try {
        //    startActivity(Intent.createChooser(email, "Choose an email client from..."));
        //} catch (ActivityNotFoundException ex) {

        //    Toast.makeText(ContactActivity.this, "No email client installed.", Toast.LENGTH_LONG).show();
        // }
    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Contact Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.trasmontano.trasmonmobile/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Contact Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.trasmontano.trasmonmobile/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
