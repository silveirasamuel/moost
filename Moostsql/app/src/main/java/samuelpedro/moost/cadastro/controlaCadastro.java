package samuelpedro.moost.cadastro;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class controlaCadastro extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://money-boost.000webhostapp.com/codigos/registra.php";
    private Map<String, String> params;

    public controlaCadastro(String name, String email, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nome", name);
        params.put("email", email);
        params.put("senha", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
