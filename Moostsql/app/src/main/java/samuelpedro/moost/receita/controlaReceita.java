package samuelpedro.moost.receita;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class controlaReceita extends StringRequest {
    private static final String RECEITA_REQUEST_URL = "https://money-boost.000webhostapp.com/codigos/addsaldo.php";
    private Map<String, String> params;

    public controlaReceita(String email, float valor,float saldo,String categoria,String data, Response.Listener<String> listener) {
        super(Method.POST, RECEITA_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("valor", valor+"");
        params.put("saldo", saldo+"");
        params.put("categoria", categoria);
        params.put("data",data);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
