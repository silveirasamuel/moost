package samuelpedro.moost.pesquisa;

/**
 * Created by erick on 09/10/2017.
 */

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class controlaBalanco extends StringRequest {
    private static final String BALANCO_REQUEST_URL = "https://money-boost.000webhostapp.com/codigos/PesquisaBalanco.php";
    private Map<String, String> params;

    public controlaBalanco(String email,String datainicio,String datafim, Response.Listener<String> listener) {
        super(Method.POST, BALANCO_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("datainicio", datainicio);
        params.put("datafim", datafim);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
