package br.com.trasmontano.trasmonmobile.domain.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import br.com.trasmontano.trasmonmobile.R;
import br.com.trasmontano.trasmonmobile.domain.model.EntrevistaQualificadaQuestao;

/**
 * Created by fmuniz on 26/08/2016.
 */
public class CustomArrayAdapterEntrevistaQualificadaQuestoes extends ArrayAdapter<EntrevistaQualificadaQuestao> {
    protected LayoutInflater inflater;
    protected int layout;

    public CustomArrayAdapterEntrevistaQualificadaQuestoes(Activity activity, int resourceId, List<EntrevistaQualificadaQuestao> objects) {
        super(activity, resourceId, objects);
        layout = resourceId;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = inflater.inflate(layout, parent, false);

        TextView txtQuestao = (TextView)view.findViewById(R.id.txtQuestao);
        TextView txtResposta = (TextView)view.findViewById(R.id.txtResposta);

        txtQuestao.setText(getItem(position).getSequencia() + "-" + getItem(position).getQuestao());

        String resposta = getItem(position).getResposta();
        String tempo = getItem(position).getTempo();
        String alegacao = getItem(position).getAlegacao();

        if(tempo != null && tempo != ""){
            resposta += " - " + tempo;
        }

        if(alegacao != null && alegacao != ""){
            resposta += " - " + alegacao;
        }

        txtResposta.setText(resposta);

        return view;
    }
}