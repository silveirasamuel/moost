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
public class CDB extends android.support.v4.app.Fragment {
    private TextView totalCDB;
    private TextView rendimentoCDB;
    private TextView totalLiquido;
    private TextView impostoDeRenda;
    private Button calcularCDB;
    private EditText valorAplicadoCDB;
    private EditText numeroMesesCDB;
    private EditText taxaSELICCDB;
    private EditText percentualCDICDB;
    private Button duvida;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(samuelpedro.moost.R.layout.cdb, container, false);
        valorAplicadoCDB= (EditText) view.findViewById(samuelpedro.moost.R.id.valorAplicadoCDB);
        numeroMesesCDB = (EditText) view.findViewById(samuelpedro.moost.R.id.numeroMesesCDB);
        taxaSELICCDB = (EditText) view.findViewById(samuelpedro.moost.R.id.taxaSELICCDB);
        percentualCDICDB = (EditText) view.findViewById(samuelpedro.moost.R.id.percentualCDICDB);
        calcularCDB = (Button)view.findViewById(samuelpedro.moost.R.id.botaoCalcularCDB);
        duvida = (Button)view.findViewById(samuelpedro.moost.R.id.duvidaCDB);

        calcularCDB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(valorAplicadoCDB.getText().toString().length()>0 && numeroMesesCDB.getText().toString().length()>0 && taxaSELICCDB.getText().toString().length()>0 && percentualCDICDB.getText().toString().length()>0 ) {
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
                builder.setMessage("Certificados de Depósito Bancário (CDB)\n\n" +
                        "Como investir: Abrindo uma conta num banco que os ofereça ou numa corretora " +
                        "independente que ofereça CDBs de diversos bancos.\n\n" +
                        "Ao aplicar em CDBs o investidor empresta seu dinheiro para uma instituição financeira e " +
                        "recebe uma remuneração por isso. Em outras palavras, o banco pega o seu dinheiro em " +
                        "uma ponta para emprestá-lo na outra, pagando uma taxa menor para captar do que " +
                        "aquela cobrada para emprestar, o que gera lucros para a instituição.\n\n" +
                        "Existem três remunerações possíveis nesta operação: pré-fixada, pós-fixada e pré e " +
                        "pós-fixada ao mesmo tempo. Na remuneração pré-fixada, a taxa de juro paga é " +
                        "determinada no momento da aplicação; se o título for pós-fixado, o rendimento é um " +
                        "percentual da Selic (menos comum) ou do CDI (Certificado de Depósito Interbancário) – " +
                        "taxa que baliza as operações interbancárias e fica muito próxima à Selic.\n\n" +
                        "Na remuneração pré e pós-fixada, ou indexada à inflação, o banco paga ao investidor a " +
                        "variação da inflação – medida por índices como IPCA, IGP-M ou INPC -, mais uma taxa " +
                        "de juro previamente definida. Esse tipo de remuneração é menos usual.\n\n" +
                        "Os CDBs são investimentos bastante oferecidos por grandes bancos aos seus clientes, " +
                        "por isso é muito simples comprar um título desses. No entanto, normalmente aremuneração que os bancos grandes oferecem não é tão vantajosa e pode ser muito " +
                        "semelhante à da poupança.\n" +
                        "Os CDBs mais rentáveis são aqueles oferecidos pelos bancos médios, que por não " +
                        "terem uma grande rede de clientes e por apresentarem mais risco oferecem taxas altas " +
                        "para atrair mais investidores.\n\n" +
                        "Sua forma de remuneração é simples: a LFT paga ao investidor a variação da taxa Selic " +
                        "diária entre a data da compra e o vencimento do título. Como a apuração do rendimento " +
                        "vai sendo ajustada às variações Selic, o investidor não é penalizado ao vender o título " +
                        "alguns anos antes do vencimento.\n\n" +
                        "Para simular CDB no Moost, basta inserir o valor a ser aplicado, o número de meses da aplicação, a taxa SELIC de hoje (http://carteirarica.com.br/taxa-selic/), o percentual do CDI e apertar o botão calcular para obter sua simulação.")
                        .setNegativeButton("Ok", null)
                        .create()
                        .show();
            }
        });

        totalCDB = (TextView) view.findViewById(samuelpedro.moost.R.id.montanteCDB);
        totalCDB.setVisibility(View.INVISIBLE);
        rendimentoCDB = (TextView) view.findViewById(samuelpedro.moost.R.id.rendimentoCDB);
        rendimentoCDB.setVisibility(View.INVISIBLE);
        impostoDeRenda = (TextView) view.findViewById(samuelpedro.moost.R.id.impostoDeRenda);
        impostoDeRenda.setVisibility(View.INVISIBLE);
        totalLiquido  = (TextView) view.findViewById(samuelpedro.moost.R.id.totalLiquidoCDB);
        totalLiquido.setVisibility(View.INVISIBLE);
        return view;
    }

    public void calcularLCI() {
        float valorAp = Float.parseFloat( valorAplicadoCDB.getText().toString());
        int numMeses = Integer.parseInt(numeroMesesCDB.getText().toString());
        double taxaDI = Double.parseDouble(taxaSELICCDB.getText().toString());
        double percentual = Double.parseDouble(percentualCDICDB.getText().toString());
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

        double imposto = 0.0;
        if(numMeses<=6){
            imposto = 22.5;
        }else  if(numMeses>6 && numMeses <= 12){
            imposto = 20.0;
        }else  if(numMeses> 12 && numMeses <=24){
            imposto = 17.5;
        }else  if(numMeses>24){
            imposto = 15.0;
        }

        double rendimentoReais = montante - valorAp;
        DecimalFormat formato = new DecimalFormat("###,###,##0.00");
        totalCDB.setText("Total: R$ " + formato.format(montante));
        rendimentoCDB.setText("Rendimento: " +formato.format(aumento) +"%");
        impostoDeRenda.setText("IR: " + formato.format(imposto) + "%");
        totalLiquido.setText("Total Líq.: R$ " + formato.format(montante - (rendimentoReais*(imposto/100))));
        totalCDB.setVisibility(View.VISIBLE);
        rendimentoCDB.setVisibility(View.VISIBLE);
        impostoDeRenda.setVisibility(View.VISIBLE);
        totalLiquido.setVisibility(View.VISIBLE);

    }

}
