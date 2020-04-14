package samuelpedro.moost.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class controlaLogin extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://money-boost.000webhostapp.com/codigos/Loga.php";
    private Map<String, String> params;

    public controlaLogin(String email, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("senha", password);
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
