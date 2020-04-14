package samuelpedro.moost.pesquisa;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class controlaPesquisa extends StringRequest {
    private static final String DESPESA_REQUEST_URL = "https://money-boost.000webhostapp.com/codigos/PesquisaGrafico.php";
    private Map<String, String> params;

    public controlaPesquisa(String email,String tipo,String datainicio,String datafim, Response.Listener<String> listener) {
        super(Method.POST, DESPESA_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("tipo", tipo);
        params.put("datainicio", datainicio);
        params.put("datafim", datafim);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
