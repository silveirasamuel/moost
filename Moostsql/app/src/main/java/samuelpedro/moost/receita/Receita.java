package samuelpedro.moost.receita;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import samuelpedro.moost.R;


public class Receita extends AppCompatActivity {
    private float valorreceita=0;
    private String DATA;
    private Spinner categoriasReceita;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(samuelpedro.moost.R.layout.activity_receita);
        System.out.println("a3");

        Calendar cal = Calendar.getInstance();
        int ano = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        String dataHojeString = dia+"/"+(mes+1)+"/"+ano;
        DATA = ano + "/" + (mes+1) + "/" + dia;



        mDisplayDate = (TextView)findViewById(R.id.dataReceita);
        mDisplayDate.setText(dataHojeString);

        categoriasReceita = (Spinner) findViewById(samuelpedro.moost.R.id.catRec);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, samuelpedro.moost.R.array.categoriasReceita, android.R.layout.simple_expandable_list_item_1);
        categoriasReceita.setAdapter(adapter);


        ConectaBD();


        mDisplayDate = (TextView)findViewById(samuelpedro.moost.R.id.dataReceita);

        mDisplayDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Receita.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,ano, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                mes++; //porque janeiro é 0!
                Log.d("Receita", "onDateSet: " + dia + "/"+mes + "/"+ano);
                DATA = ano + "/" + mes + "/" + dia;
                String data = dia +"/"+ mes +"/" + ano;
                mDisplayDate.setText(data);

            }
        };

    }
    public void ConectaBD(){
        final EditText valor = (EditText) findViewById(samuelpedro.moost.R.id.receita);
        final Button breceita = (Button) findViewById(samuelpedro.moost.R.id.button2);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando...");
        progressDialog.setCancelable(false);
        breceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(valor.getText().length()<=0 ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Insira o valor!")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                    return;
                }

                progressDialog.show();
                System.out.println("a2");
                String valordigitado = valor.getText().toString();
                valorreceita=Float.parseFloat(valordigitado);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String success = jsonResponse.getString("success");
                            if (success.equals("vazio")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(Receita.this);
                                builder.setMessage("Preencha todos os campos")
                                        .setNegativeButton("OK", null)
                                        .create()
                                        .show();
                            }
                            if (success.equals("ok")) {
                                System.out.println("hii");
                                Intent intent = new Intent(Receita.this, samuelpedro.moost.main.MainActivity.class);
                                Receita.this.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                String categoria;
                categoria = categoriasReceita.getSelectedItem().toString();
                samuelpedro.moost.login.Login.saldo+=valorreceita;
                System.out.println(DATA);
                controlaReceita receitaRequest = new controlaReceita(samuelpedro.moost.login.Login.login, valorreceita,samuelpedro.moost.login.Login.saldo,categoria,DATA, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Receita.this);
                queue.add(receitaRequest);

            }
        });
        progressDialog.cancel();
    }
}