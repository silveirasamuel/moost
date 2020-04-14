package samuelpedro.moost.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(samuelpedro.moost.R.layout.activity_main);

        saudacao();
        TextView saldo = (TextView) findViewById(samuelpedro.moost.R.id.total);
        DecimalFormat formato = new DecimalFormat("###,###,##0.00");
        saldo.setText(formato.format(samuelpedro.moost.login.Login.saldo));

        ActionBar bar = getSupportActionBar();
        Window window = this.getWindow();
        LinearLayout saudacaoLayout = (LinearLayout)findViewById(samuelpedro.moost.R.id.layoutSaudacao);
        if(samuelpedro.moost.login.Login.saldo <200 && samuelpedro.moost.login.Login.saldo > 0){
            bar = getSupportActionBar();
            window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#0099ff"));
            saudacaoLayout.setBackground(new ColorDrawable(Color.parseColor("#0099ff")));
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0099ff")));
        }else
        if(samuelpedro.moost.login.Login.saldo <= 0){
            bar = getSupportActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DF0101")));
            window = this.getWindow();
            saudacaoLayout = (LinearLayout)findViewById(samuelpedro.moost.R.id.layoutSaudacao);
            saudacaoLayout.setBackground(new ColorDrawable(Color.parseColor("#DF0101")));
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#DF0101"));
        }
    }




    private void saudacao(){
        TextView textnome = (TextView) findViewById(samuelpedro.moost.R.id.BomDia);
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String saudacao;

        if(hour >= 6 && hour <= 12) {
            saudacao= "Bom dia, " + samuelpedro.moost.login.Login.name;
        }
        else if(hour>12 && hour < 19) {
            saudacao= "Boa tarde, " + samuelpedro.moost.login.Login.name;
        }
        else {
            saudacao= "Boa noite, " + samuelpedro.moost.login.Login.name;
        }
        textnome.setText(saudacao);
    }


    public void adicionarReceita(View v){
        Intent receita = new Intent(this, samuelpedro.moost.receita.Receita.class);
        startActivity(receita);
    }
    public void adicionarDespesa(View v){
        Intent despesa = new Intent(this, samuelpedro.moost.despesa.Despesas.class);
        startActivity(despesa);
    }

    public void investimentos(View v){
        Intent investimentos = new Intent(this, samuelpedro.moost.fragmento.investimento.Investimentos.class);
        startActivity(investimentos);
    }

    public void graficos(View v){
        Intent graficos = new Intent(this, samuelpedro.moost.fragmento.graficos.GraficosMoost.class);
        startActivity(graficos);
    }

    public void visaoGeral(View v){
        Intent visaoGeral = new Intent(this, samuelpedro.moost.visaogeral.VisaoGeral.class);
        startActivity(visaoGeral);
    }


    public void sobre(View v){
        Intent sobre = new Intent(this, samuelpedro.moost.sobre.Sobre.class);
        startActivity(sobre);
    }
}
