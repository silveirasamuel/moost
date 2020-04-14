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
public class LCI extends android.support.v4.app.Fragment {
    private TextView totalLCI;
    private TextView rendimentoLCI;
    private Button calcularLCI;
    private EditText valorAplicadoLCI;
    private EditText numeroMesesLCI;
    private EditText taxaSELIC;
    private EditText percentualCDILCI;
    private Button duvida;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(samuelpedro.moost.R.layout.lci, container, false);
        valorAplicadoLCI= (EditText) view.findViewById(samuelpedro.moost.R.id.valorAplicadoLCI);
        numeroMesesLCI = (EditText) view.findViewById(samuelpedro.moost.R.id.numeroMesesLCI);
        taxaSELIC = (EditText) view.findViewById(samuelpedro.moost.R.id.taxaSELICLCI);
        percentualCDILCI = (EditText) view.findViewById(samuelpedro.moost.R.id.percentualCDILCI);
        calcularLCI = (Button)view.findViewById(samuelpedro.moost.R.id.botaoCalcularLCI);
        duvida = (Button)view.findViewById(samuelpedro.moost.R.id.duvidaLCI);

        calcularLCI.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(valorAplicadoLCI.getText().toString().length()>0 && numeroMesesLCI.getText().toString().length()>0 && taxaSELIC.getText().toString().length()>0 && percentualCDILCI.getText().toString().length()>0 ) {
                    calcularLCI();
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
                builder.setMessage("Letra de Crédito Imobiliário (LCI) e Letra de Crédito do Agronegócio (LCA)\n\n" +
                        "A Letra de Crédito Imobiliário (LCI) e a Letra de Crédito do Agronegócio (LCA) são " +
                        "títulos emitidos pelos bancos para obtenção de recursos destinados a financiamentos " +
                        "do setor imobiliário e agrícola, respectivamente.\n\n" +
                        "Ambos os títulos são bem parecidos com os CDBs, mas têm a vantagem de serem " +
                        "isentos de imposto de renda, o que faz uma grande diferença no rendimento final. O " +
                        "governo concede a isenção para incentivar a concessão de crédito a esses setores. " +
                        "Com a isenção do imposto de renda, uma LCI ou uma LCA que pague 80% do CDI já é " +
                        "mais rentável que um CDB que rende 93% do CDI, em qualquer prazo, mesmo depois " +
                        "de dois anos, quando é aplicado o menor desconto do imposto de renda sobre o CDB," +
                        "de 15%.\n\n" +
                        "A principal desvantagem da LCI e da LCA é que elas são menos acessíveis que os " +
                        "CDBs. No Banco do Brasil o aporte mínimo é de 30 mil reais, no Santander é de 250 mil " +
                        "reais e na Caixa é de 5 milhões de reais. Além disso, a liquidez pode ser baixa, ou seja, " +
                        "o investidor pode não conseguir resgatar o dinheiro a qualquer momento, o que " +
                        "representa um entrave, comparado à caderneta de poupança.\n\n" +
                        "Como investir: Abrindo uma conta num banco que as ofereça ou numa corretora\n" +
                        "independente que ofereça LCIs e LCAs de diversos bancos.\n\n" +
                        "Para simular LCI no Moost, basta inserir o valor a ser aplicado, " +
                        "seguido do tempo em meses da aplicação, a taxa selic anual de hoje (http://carteirarica.com.br/taxa-selic/) " +
                        "e o percentual do CDI. Após preencher os campos basta clicar em calcular e obter sua simulação.")
                        .setNegativeButton("Ok", null)
                        .create()
                        .show();
            }
        });

        totalLCI = (TextView) view.findViewById(samuelpedro.moost.R.id.montanteLCI);
        totalLCI.setVisibility(View.INVISIBLE);
        rendimentoLCI = (TextView) view.findViewById(samuelpedro.moost.R.id.rendimentoLCI);
        rendimentoLCI.setVisibility(View.INVISIBLE);
        return view;
    }

    public void calcularLCI() {
        float valorAp = Float.parseFloat( valorAplicadoLCI.getText().toString());
        int numMeses = Integer.parseInt(numeroMesesLCI.getText().toString());
        double taxaDI = Double.parseDouble(taxaSELIC.getText().toString());
        double percentual = Double.parseDouble(percentualCDILCI.getText().toString());
        double rendimentoReal = (percentual/100f) * (taxaDI/100f);

        /*Cálculos simuladores do investimento*/
        double rendimentoMensal = Math.pow((1f+rendimentoReal),(1f/12f))-1f;
        /*System.out.println("Taxa DI:" + taxaDI);
        System.out.println("Taxa percentual:" + percentual);
        System.out.println("Rendimento real por ano: " + rendimentoReal);
        System.out.println("Rendimento real por mes: " + rendimentoMensal);*/
        double montante =  valorAp *Math.pow((1f+rendimentoMensal), numMeses);
        //System.out.println("Montante: " + montante);
        double aumento = (montante/valorAp-1) * 100f;

        DecimalFormat formato = new DecimalFormat("###,###,##0.00");
        totalLCI.setText("Total: R$ " + formato.format(montante));
        rendimentoLCI.setText("Rendimento: " +formato.format(aumento) +"%");
        totalLCI.setVisibility(View.VISIBLE);
        rendimentoLCI.setVisibility(View.VISIBLE);

    }

}
