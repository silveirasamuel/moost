package samuelpedro.moost.despesa;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class controlaDespesa extends StringRequest {
    private static final String DESPESA_REQUEST_URL = "https://money-boost.000webhostapp.com/codigos/removesaldo.php";
    private Map<String, String> params;

    public controlaDespesa(String email, float valor,float saldo,String categoria,String data, Response.Listener<String> listener) {
        super(Method.POST, DESPESA_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("valor", valor+"");
        params.put("saldo", saldo+"");
        params.put("categoria", categoria);
        params.put("data", data);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
