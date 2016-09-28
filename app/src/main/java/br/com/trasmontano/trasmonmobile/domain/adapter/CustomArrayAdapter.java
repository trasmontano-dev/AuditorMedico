package br.com.trasmontano.trasmonmobile.domain.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.trasmontano.trasmonmobile.R;
import br.com.trasmontano.trasmonmobile.domain.model.ItemCarencia;

/**
 * Created by fmuniz on 18/06/2016.
 */
public class CustomArrayAdapter extends ArrayAdapter<ItemCarencia> {
    protected LayoutInflater inflater;
    protected int layout;

    public CustomArrayAdapter(Activity activity, int resourceId, List<ItemCarencia> objects){
        super(activity, resourceId, objects);
        layout = resourceId;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = inflater.inflate(layout, parent, false);
        TextView textView = (TextView)view.findViewById(R.id.descricaoCarencia);
        ImageView imageView = (ImageView)view.findViewById(R.id.iconeSituacao);
        TextView textViewDataCarencia  = (TextView)view.findViewById(R.id.textViewDataCarencia);

        textView.setText(getItem(position).getDescricaoCarencia());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String input = getItem(position).getDataCarencia().substring(0, 10);
        Date date;
        try {
            date = dateFormat.parse(input);
            input = new SimpleDateFormat("dd/MM/yyyy").format(date);
            textViewDataCarencia.setText(input);
        } catch (ParseException ex){
            Log.d("ERRO-------->", ex.getMessage().toString());
        }

        if(getItem(position).getSituacao().toUpperCase().equals("LIBERADO")){
            imageView.setImageResource(R.drawable.check);
        } else {
            imageView.setImageResource(R.drawable.close);
        }

        return view;
    }
}
