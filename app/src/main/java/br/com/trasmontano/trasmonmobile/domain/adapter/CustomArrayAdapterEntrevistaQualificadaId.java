package br.com.trasmontano.trasmonmobile.domain.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.trasmontano.trasmonmobile.R;
import br.com.trasmontano.trasmonmobile.domain.model.EntrevistaQualificada;

/**
 * Created by fmuniz on 24/08/2016.
 */
public class CustomArrayAdapterEntrevistaQualificadaId extends ArrayAdapter<Integer> {
    protected LayoutInflater inflater;
    protected int layout;

    public CustomArrayAdapterEntrevistaQualificadaId(Activity activity, int resourceId, List<Integer> objects){
        super(activity, resourceId, objects);
        layout = resourceId;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = inflater.inflate(layout, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.txtId);
        textView.setText(getItem(position).toString());

        return view;
    }
}