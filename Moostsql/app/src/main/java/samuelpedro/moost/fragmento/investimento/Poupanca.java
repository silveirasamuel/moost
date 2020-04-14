package samuelpedro.moost.fragmento.investimento;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by samuel on 8/27/17.
 */
public class Poupanca extends android.support.v4.app.Fragment {
    private TextView total;
    private TextView rendimento;
    private Button calcular;
    private EditText valorAplicado;
    private EditText numeroMeses;
    private EditText juros;
    private Button duvida;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(samuelpedro.moost.R.layout.poupanca, container, false);
        duvida = (Button)view.findViewById(samuelpedro.moost.R.id.duvidaPoupanca);
        valorAplicado = (EditText) view.findViewById(samuelpedro.moost.R.id.valorAplicado);
        numeroMeses = (EditText) view.findViewById(samuelpedro.moost.R.id.numeroMeses);
        juros = (EditText) view.findViewById(samuelpedro.moost.R.id.juros);
        calcular = (Button)view.findViewById(samuelpedro.moost.R.id.botaoCalcularPoupanca);
        calcular.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(valorAplicado.getText().toString().length()>0 && numeroMeses.getText().toString().length()>0 && juros.getText().toString().length()>0 ) {
                    calcularPoupanca();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Campos nulos não permitidos!")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
                }
            }
        });

        duvida.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Poupança\n\n" +
                            "Uma pesquisa realizada pelo Serviço de Proteção ao Crédito (SPC Brasil) e pela " +
                            "Confederação Nacional de Dirigentes Lojistas (CNDL) investigou quais são os produtos, " +
                            "serviços financeiros e investimentos que os brasileiros possuem e mostra que a poupança é a " +
                            "modalidade de investimento mais popular, citada por 69,5% dos entrevistados em todo o Brasil.\n\n" +
                            "Mas, mesmo assim, nota-se que a poupança não é um investimento a ser sugerido com " +
                            "veemência depois o valor do lucro a ser obtido é 0,5% mais a inflação do período. Este " +
                            "período corresponde a 30 dias a contar da data de aniversário do depósito, e esse valor é o " +
                            "mesmo independente do banco.\n\n" +
                            "Para utilizar o simulador de poupança no Moost, basta digitar o valor a ser aplicado, a quantidade de meses que se deseja " +
                            "poupar o dinheiro e inserir a taxa (geralmente 0,51%) de juros. Após isso clique no botão calcular e veja as simulação.\n\n" +
                            "Caso o resultado seja favorável, vá até uma agência bancária e abra uma conta poupança!")
                            .setNegativeButton("Ok", null)
                            .create()
                            .show();
            }
        });
        total = (TextView) view.findViewById(samuelpedro.moost.R.id.montante);
        total.setVisibility(View.INVISIBLE);
        rendimento = (TextView) view.findViewById(samuelpedro.moost.R.id.rendimento);
        rendimento.setVisibility(View.INVISIBLE);
        return view;
    }

    public void calcularPoupanca(){
        double valorAp = Double.parseDouble( valorAplicado.getText().toString());
        int numMeses = Integer.parseInt(numeroMeses.getText().toString());
        double jurosFloat = Double.parseDouble(juros.getText().toString());
        double montante = valorAp * Math.pow((1f+(jurosFloat/100f)),numMeses);
        double aumento = montante - valorAp;
        double rendimentoCalculado =  aumento/valorAp * 100f;

        DecimalFormat formato = new DecimalFormat("###,###,##0.00");
        total.setText("Total: R$ " + formato.format(montante));
        rendimento.setText("Rendimento: " +formato.format(rendimentoCalculado) +"%");
        total.setVisibility(View.VISIBLE);
        rendimento.setVisibility(View.VISIBLE);
    }

}
