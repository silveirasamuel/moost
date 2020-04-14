package samuelpedro.moost.despesa;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
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

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import samuelpedro.moost.R;
import samuelpedro.moost.login.Login;

public class Despesas extends Activity {

    private float valordespesa = 0;
    private String DATA;
    private Spinner categoriasDespesa;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Date dataHoje, dataDespesa;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(samuelpedro.moost.R.layout.activity_despesas);


        Calendar cal = Calendar.getInstance();
        int ano = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        dataHoje = new Date(ano, mes,dia);
        String dataHojeString = dia+"/"+(mes+1)+"/"+ano;
        dataDespesa = new Date(ano,mes,dia);
        DATA = ano + "/" + (mes+1) + "/" + dia;



        mDisplayDate = (TextView)findViewById(samuelpedro.moost.R.id.dataDespesa);
        mDisplayDate.setText(dataHojeString);
        System.out.print("ANOO:" +ano);
        categoriasDespesa = (Spinner) findViewById(samuelpedro.moost.R.id.catDesp);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, samuelpedro.moost.R.array.categoriasDespesa, android.R.layout.simple_expandable_list_item_1);
        categoriasDespesa.setAdapter(adapter);



        mDisplayDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Despesas.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener,ano, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                mes++;//pq janeiro é 0
                DATA = ano + "/" + mes + "/" + dia;
                Log.d("Despesa", "onDateSet: " + dia + "/"+mes + "/"+ano);
                String data = dia +"/"+ mes +"/" + ano;
                dataDespesa = new Date(ano,mes-1,dia);
                format.format(dataDespesa);
                System.out.println("Data Desp:" + dataDespesa);
                System.out.println("Data Hoje:" + dataHoje);
                mDisplayDate.setText(data);
            }
        };
        ConectaBD();
    }

    public void ConectaBD() {
        System.out.println("a1");
        final EditText valor = (EditText) findViewById(samuelpedro.moost.R.id.despesa);
        final Button breceita = (Button) findViewById(samuelpedro.moost.R.id.buttondespesa);
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando...");
        progressDialog.setCancelable(false);
        breceita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valor.getText().length()<=0 ){
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
                    builder.setMessage("Insira o valor!")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                    return;
                }

                progressDialog.show();
                System.out.println("a2");
                String valordigitado = valor.getText().toString();
                valordespesa=Float.parseFloat(valordigitado)*(-1);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String success = jsonResponse.getString("success");
                            if (success.equals("vazio")){
                                AlertDialog.Builder builder = new AlertDialog.Builder(Despesas.this);
                                builder.setMessage("Preencha todos os campos")
                                        .setNegativeButton("OK", null)
                                        .create()
                                        .show();
                            }
                            if (success.equals("ok")) {
                                System.out.println("hii");
                                if(dataHoje.before(dataDespesa)) {
                                    notifica();
                                    agendaNotificacao();
                                }
                                Intent intent = new Intent(Despesas.this, samuelpedro.moost.main.MainActivity.class);
                                Despesas.this.startActivity(intent);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                System.out.println("a33333");
                String categoria;
                categoria = categoriasDespesa.getSelectedItem().toString();
                System.out.println(categoria);
                samuelpedro.moost.login.Login.saldo+=valordespesa;
                System.out.println(DATA);
                System.out.println(samuelpedro.moost.login.Login.saldo);
                controlaDespesa despesaRequest = new controlaDespesa(samuelpedro.moost.login.Login.login, valordespesa,samuelpedro.moost.login.Login.saldo,categoria,DATA, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Despesas.this);
                queue.add(despesaRequest);
                System.out.println("a444");

            }
        });
        progressDialog.cancel();

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void agendaNotificacao() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        Calendar diaDePagamento = Calendar.getInstance();
        diaDePagamento.set(Calendar.YEAR, dataDespesa.getYear());
        diaDePagamento.set(Calendar.MONTH, dataDespesa.getMonth());
        diaDePagamento.set(Calendar.DAY_OF_MONTH, dataDespesa.getDay());
        diaDePagamento.set(Calendar.HOUR_OF_DAY,7);
        diaDePagamento.set(Calendar.MINUTE, 0);
        diaDePagamento.set(Calendar.SECOND, 0);
        int dif = (int) ((-1)* (diaDePagamento.getTimeInMillis() - cal.getTimeInMillis()));
        System.out.println("DIFERENCAAAAAAAA " + dif);
        System.out.println("HOJE " + cal.getTimeInMillis());
        System.out.println("PAGAMENTO " + diaDePagamento.getTimeInMillis());
        cal.add(Calendar.MILLISECOND,dif);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
    }

    private void notifica(){
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this, Login.class),0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("Moost");
        builder.setContentTitle("Moost");
        builder.setContentText("Despesa agendada!");
        builder.setSmallIcon(R.drawable.icone);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon));
        builder.setContentIntent(p);
        Notification n = builder.build();
        n.vibrate = new long[]{150,300,150,600};
        nm.notify(R.drawable.icon, n);

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(this,som);
            toque.play();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

