package br.com.trasmontano.trasmonmobile.domain.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.trasmontano.trasmonmobile.R;
import br.com.trasmontano.trasmonmobile.domain.model.UltimasConsultas;

/**
 * Created by fmuniz on 19/07/2016.
 */
public class CustomArrayAdapterUltimasConsultas extends ArrayAdapter<UltimasConsultas> {
    protected LayoutInflater inflater;
    protected int layout;

    public CustomArrayAdapterUltimasConsultas(Activity activity, int resourceId, List<UltimasConsultas> objects){
        super(activity, resourceId, objects);
        layout = resourceId;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(layout, parent, false);

        TextView textViewTermo = (TextView)view.findViewById(R.id.textViewTermo);
        TextView textViewDataEmissao = (TextView)view.findViewById(R.id.textViewDataEmissao);
        TextView textViewCaraterAtendimento = (TextView)view.findViewById(R.id.textViewCaraterAtendimento);
        TextView textViewRede = (TextView)view.findViewById(R.id.textViewRede);

        textViewTermo.setText(getItem(position).getTermo());
        textViewCaraterAtendimento.setText(getItem(position).getCaraterAtendimento().equals("E") ? "Eletiva" : "Urgência");
        textViewRede.setText(getItem(position).getRede());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String input = getItem(position).getDataEmissao().substring(0, 10);
        Date date;
        try {
            date = dateFormat.parse(input);
            input = new SimpleDateFormat("dd/MM/yyyy").format(date);
            textViewDataEmissao.setText(input);
        } catch (ParseException ex){
            Log.d("ERRO-------->", ex.getMessage().toString());
        }

        return view;
    }
}
