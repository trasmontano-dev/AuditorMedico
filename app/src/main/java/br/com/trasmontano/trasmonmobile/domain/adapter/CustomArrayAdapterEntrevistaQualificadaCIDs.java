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
import br.com.trasmontano.trasmonmobile.domain.model.EntrevistaQualificadaCID;

/**
 * Created by fmuniz on 26/08/2016.
 */
public class CustomArrayAdapterEntrevistaQualificadaCIDs extends ArrayAdapter<EntrevistaQualificadaCID> {
    protected LayoutInflater inflater;
    protected int layout;

    public CustomArrayAdapterEntrevistaQualificadaCIDs(Activity activity, int resourceId, List<EntrevistaQualificadaCID> objects){
        super(activity, resourceId, objects);
        layout = resourceId;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = inflater.inflate(layout, parent, false);

        TextView txtCID = (TextView)view.findViewById(R.id.txtCID);
        TextView txtDescricao = (TextView)view.findViewById(R.id.txtDescricao);

        txtCID.setText(getItem(position).getItem() + " - CID " + getItem(position).getCID());
        txtDescricao.setText(getItem(position).getDescricao());

        return view;
    }
}