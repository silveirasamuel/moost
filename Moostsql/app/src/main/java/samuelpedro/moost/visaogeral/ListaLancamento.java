package samuelpedro.moost.visaogeral;

/**
 * Created by erick on 06/09/2017.
 */

public class ListaLancamento {
    private String Valor;
    private String Data;
    private String Categoria;
    public int IdLancamento;



    public ListaLancamento(String v, String d,String c,int id) {
       Valor = v;
        Data = d;
        Categoria = c;
        IdLancamento = id;
    }

    public String getCategoria() {
        return Categoria;
    }
    public String getData() {
        return Data;
    }
    public String getValor() {
        return Valor;
    }
    public int getId() {
        return IdLancamento;
    }

}
