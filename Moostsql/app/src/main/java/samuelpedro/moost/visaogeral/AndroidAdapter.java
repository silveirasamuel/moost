package samuelpedro.moost.visaogeral;

/**
 * Created by erick on 06/09/2017.
 */


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class AndroidAdapter extends ArrayAdapter<ListaLancamento> {
    private String tipo;

    private static final String LOG_TAG = AndroidAdapter.class.getSimpleName();

    public AndroidAdapter(Activity context, ArrayList<ListaLancamento> android) {
        super(context, 0, android);
    }
    public void setTipo(String t){
        tipo = t;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(tipo=="receita") {

            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(samuelpedro.moost.R.layout.list_itemreceita, parent, false);
            }

            ListaLancamento currentAndroid = getItem(position);

            TextView tv_valor = (TextView) listItemView.findViewById(samuelpedro.moost.R.id.tv_Rvalor);
            tv_valor.setText(currentAndroid.getValor());

            TextView tv_data = (TextView) listItemView.findViewById(samuelpedro.moost.R.id.tv_Rdata);
            tv_data.setText(currentAndroid.getData());

            TextView tv_categoria = (TextView) listItemView.findViewById(samuelpedro.moost.R.id.tv_categoria);
            tv_categoria.setText(currentAndroid.getCategoria());
        }
        if(tipo=="despesa") {

            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(samuelpedro.moost.R.layout.list_itemdespesa, parent, false);
            }

            ListaLancamento currentAndroid = getItem(position);

            TextView tv_valor = (TextView) listItemView.findViewById(samuelpedro.moost.R.id.tv_Rvalor);
            tv_valor.setText(currentAndroid.getValor());

            TextView tv_data = (TextView) listItemView.findViewById(samuelpedro.moost.R.id.tv_Rdata);
            tv_data.setText(currentAndroid.getData());

            TextView tv_categoria = (TextView) listItemView.findViewById(samuelpedro.moost.R.id.tv_categoria);
            tv_categoria.setText(currentAndroid.getCategoria());
        }

        return listItemView;
    }
}