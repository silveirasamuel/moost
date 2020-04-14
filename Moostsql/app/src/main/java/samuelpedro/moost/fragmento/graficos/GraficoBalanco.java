package samuelpedro.moost.fragmento.graficos;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import samuelpedro.moost.VolleyCallback;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

/**
 * Created by samuel on 8/3/17.
 */
public class GraficoBalanco extends android.support.v4.app.Fragment  {

    private View viewAux;
    private float balanco[] = {0f,0f};
    private List<String> ListaAux = new ArrayList<>();
    private String categoriasBalanco[] = {"Receitas","Despesas"};
    private String DATA;
    private TextView mDisplayDateInicio, mDisplayDateFinal;
    private DatePickerDialog.OnDateSetListener mDateSetListenerInicio, mDateSetListenerFinal;
    private Button gerar;
    private String dataInicioPesquisa = "", dataFinalPesquisa = "";
    private boolean verificaDataInicio=false, verificaDataFinal = false;
    private ProgressDialog progressDialog;

    private static final int[] cores = {
            rgb("#04B404"), rgb("#FF0000")
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(samuelpedro.moost.R.layout.evolucao_gastos, container, false);
        viewAux=view;

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Buscando dados...");
        progressDialog.setCancelable(false);
        Calendar cal = Calendar.getInstance();
        int ano = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        mes++;
        System.out.println("Mes:"+mes);
        String dI, dF;
        dI = ano+"/"+mes+"/01";
        dF = ano+"/"+mes+"/31";
        System.out.println("Inicio:"+dI);
        System.out.println("Final:"+dF);
        AtualizaLista(dI,dF , viewAux);


        mDisplayDateInicio = (TextView)view.findViewById(samuelpedro.moost.R.id.dataInicioBalanco);
        mDisplayDateFinal = (TextView)view.findViewById(samuelpedro.moost.R.id.dataFinalBalanco);
        gerar = (Button)view.findViewById(samuelpedro.moost.R.id.geraGraficoBalanco);


        gerar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificaDataFinal && verificaDataInicio) {
                    System.out.println("Data inicial: " + dataInicioPesquisa + "Data final: " + dataFinalPesquisa);
                    AtualizaLista(dataInicioPesquisa, dataFinalPesquisa, viewAux);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Insira a data inicial e final!")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                }

            }

        });

        mDisplayDateInicio.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListenerInicio,ano, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDisplayDateFinal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListenerFinal,ano, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerInicio = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                mes++; //porque janeiro é 0!
                Log.d("Receita", "onDateSet: " + dia + "/"+mes + "/"+ano);
                DATA = ano + "/" + mes + "/" + dia;
                String data = dia +"/"+ mes +"/" + ano;
                dataInicioPesquisa = ano +"/"+ mes +"/" + dia;
                mDisplayDateInicio.setText(data);
                verificaDataInicio = true;

            }
        };
        mDateSetListenerFinal = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                mes++; //porque janeiro é 0!
                Log.d("Receita", "onDateSet: " + dia + "/"+mes + "/"+ano);
                DATA = ano + "/" + mes + "/" + dia;
                String data = dia +"/"+ mes +"/" + ano;
                dataFinalPesquisa = ano +"/"+ mes +"/" + dia;
                mDisplayDateFinal.setText(data);
                verificaDataFinal = true;

            }
        };
        return view;
    }

    private void AtualizaLista(String dataini, String datafim, final View view){
        progressDialog.show();
        System.out.println("CARREGANDO");

        retornaDados(dataini,datafim,view,new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("que ota"+ListaAux.size());

                for(int i =0;i<ListaAux.size();i++){
                    if(Float.parseFloat(ListaAux.get(i))<0) {
                        balanco[i] = Float.parseFloat(ListaAux.get(i)) * -1;
                    }else{
                        balanco[i] = Float.parseFloat(ListaAux.get(i));
                    }
                }
                setupPieChartBalanco(view);
                progressDialog.cancel();
            }
        });
    }


    public void retornaDados(final String datainicio,final String datafim, final View view,final VolleyCallback callback) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("No try do JSON");
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    System.out.println("Sucesso" + success);
                    if (success) {
                        System.out.println("No try do JSON2");
                        ListaAux.clear();
                        ListaAux.add(jsonResponse.getString("receita"));
                        ListaAux.add(jsonResponse.getString("despesa"));
                        for(int i =0;i<ListaAux.size();i++) {
                            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                            System.out.println(ListaAux.get(i));
                        }
                    } else {
                        System.out.println("Não é pra entra aqui NUNCA, no php sempre vai da true");
                    }
                    callback.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        samuelpedro.moost.pesquisa.controlaBalanco pesquisaRequest = new samuelpedro.moost.pesquisa.controlaBalanco(samuelpedro.moost.login.Login.login, datainicio, datafim, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        queue.add(pesquisaRequest);
    }


    private void setupPieChartBalanco(View view) {
        //Popultaing a list of PieEntries
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < balanco.length; i++) {
            if(balanco[i]>1) {
                pieEntries.add(new PieEntry(balanco[i], categoriasBalanco[i]));
            }

        }

        PieDataSet dataSet = new PieDataSet(pieEntries,"");
        dataSet.setColors(cores);
        dataSet.setValueTextSize(12);


        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());


        PieChart chart = (PieChart) view.findViewById(samuelpedro.moost.R.id.balanco);

        chart.setData(data);
        chart.setUsePercentValues(true);
        chart.setCenterText("Balanço");
        chart.setCenterTextSize(17);
        chart.setCenterTextColor(Color.parseColor("#FE9A2E"));
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.invalidate();

        return;
    }


}
