package samuelpedro.moost.visaogeral;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import samuelpedro.moost.VolleyCallback;

public class VisaoGeral extends AppCompatActivity {

    private ArrayList<ListaLancamento> ListaDeDados;
    private String data, valor, categoria;
    private Button receitaBotao, despesaBotao;
    private ProgressDialog progressDialog;

    public String DATA;
    private TextView mDisplayDateInicio, mDisplayDateFinal;
    private DatePickerDialog.OnDateSetListener mDateSetListenerInicio, mDateSetListenerFinal;
    private Button gerar;
    private String dataInicioPesquisa = "", dataFinalPesquisa = "";
    boolean verificaDataInicio = false, verificaDataFinal = false, verificaTipo = false;
    private String tipo;
    private TextView textoReceita, textoDespesa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(samuelpedro.moost.R.layout.activity_visao_geral);

        textoDespesa = (TextView) findViewById(samuelpedro.moost.R.id.despesaTexto);
        textoReceita = (TextView) findViewById(samuelpedro.moost.R.id.receitaTexto);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.setCancelable(false);
        Calendar cal = Calendar.getInstance();
        int ano = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        mes++;
        System.out.println("Mes:" + mes);
        String dI, dF;
        dI = ano + "/" + mes + "/01";
        dF = ano + "/" + mes + "/31";
        System.out.println("Inicio:" + dI);
        System.out.println("Final:" + dF);
        AtualizaLista(dI, dF, "despesa");

        receitaBotao = (Button) findViewById(samuelpedro.moost.R.id.visaoReceita);
        despesaBotao = (Button) findViewById(samuelpedro.moost.R.id.visaoDespesa);

        mDisplayDateInicio = (TextView) findViewById(samuelpedro.moost.R.id.dataInicioVisao);
        mDisplayDateFinal = (TextView) findViewById(samuelpedro.moost.R.id.dataFinalVisao);
        gerar = (Button) findViewById(samuelpedro.moost.R.id.geraVisao);


        gerar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verificaDataFinal && verificaDataInicio && verificaTipo) {
                    System.out.println("Data inicial: " + dataInicioPesquisa + "Data final: " + dataFinalPesquisa);
                    AtualizaLista(dataInicioPesquisa, dataFinalPesquisa, tipo);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Insira a data inicial e final ou selecione um tipo!")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                }

            }

        });

        mDisplayDateInicio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(VisaoGeral.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListenerInicio, ano, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDisplayDateFinal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(VisaoGeral.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListenerFinal, ano, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerInicio = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                mes++; //porque janeiro é 0!
                Log.d("Receita", "onDateSet: " + dia + "/" + mes + "/" + ano);
                DATA = ano + "/" + mes + "/" + dia;
                String data = dia + "/" + mes + "/" + ano;
                dataInicioPesquisa = ano + "/" + mes + "/" + dia;
                mDisplayDateInicio.setText(data);
                verificaDataInicio = true;

            }
        };
        mDateSetListenerFinal = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                mes++; //porque janeiro é 0!
                Log.d("Receita", "onDateSet: " + dia + "/" + mes + "/" + ano);
                DATA = ano + "/" + mes + "/" + dia;
                String data = dia + "/" + mes + "/" + ano;
                dataFinalPesquisa = ano + "/" + mes + "/" + dia;
                mDisplayDateFinal.setText(data);
                verificaDataFinal = true;

            }
        };
        receitaBotao.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipo = "receita";
                verificaTipo = true;
                textoReceita.setTextColor(Color.parseColor("#3366FF"));
                textoDespesa.setTextColor(Color.parseColor("#666666"));
            }
        });

        despesaBotao.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipo = "despesa";
                verificaTipo = true;
                textoReceita.setTextColor(Color.parseColor("#666666"));
                textoDespesa.setTextColor(Color.parseColor("#3366FF"));
            }
        });
    }


    private void AtualizaLista(String datainicio, String datafim, String tipo) {
        ListaDeDados = new ArrayList<ListaLancamento>();
        System.out.println("CARREGANDO");
        progressDialog.show();
        retornaDados(datainicio, datafim, tipo, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("CARREGou");
                progressDialog.cancel();
            }
        });
    }

    public void retornaDados(final String datainicio, final String datafim, final String tipo, final VolleyCallback callback) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        categoria = jsonResponse.getJSONArray(i).getString(1);
                        DecimalFormat formato = new DecimalFormat("###,###,##0.00");
                        double valorDouble = jsonResponse.getJSONArray(i).getDouble(2);
                        int id = jsonResponse.getJSONArray(i).getInt(5);
                        if (tipo == "despesa") {
                            valorDouble *= -1;
                        }

                        valor = "R$ " + formato.format(valorDouble);
                        ;
                        data = jsonResponse.getJSONArray(i).getString(3);
                        System.out.println(categoria);
                        System.out.println(data);
                        System.out.println(valor);
                        System.out.println(id);
                        ListaDeDados.add(new ListaLancamento(valor, data, categoria,id));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("testevisaogeral: ---" + ListaDeDados.size());
                list(ListaDeDados, tipo);
                callback.onSuccess(response);
            }
        };
        controlaVisao pesquisaRequest = new controlaVisao(samuelpedro.moost.login.Login.login, datainicio, datafim, tipo, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(pesquisaRequest);
    }

    public void list(final ArrayList<samuelpedro.moost.visaogeral.ListaLancamento> lista, String tipo) {
        AndroidAdapter adapter = new AndroidAdapter(this, lista);
        adapter.setTipo(tipo);
        final ListView listView = (ListView) findViewById(samuelpedro.moost.R.id.visaoGeral);
        listView.setAdapter(adapter);

          //  *****  Vou usar no futuro *****
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("hahuhui"+ ListaDeDados.get(position).getValor());
            }
        });


    }
}
