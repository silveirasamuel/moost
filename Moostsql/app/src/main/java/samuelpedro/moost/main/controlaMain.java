package samuelpedro.moost.main;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class controlaMain extends StringRequest {
    private static final String MENU_REQUEST_URL = "https://money-boost.000webhostapp.com/codigos/mostrasaldo.php";
    private Map<String, String> params;

    public controlaMain( String email, Response.Listener<String> listener) {
        super(Method.POST, MENU_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
