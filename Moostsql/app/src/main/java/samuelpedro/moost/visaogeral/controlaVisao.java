package samuelpedro.moost.visaogeral;

/**
 * Created by erick on 05/09/2017.
 */
        import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;

        import java.util.HashMap;
        import java.util.Map;

public class controlaVisao extends StringRequest{

    private static final String PESQUISA_REQUEST_URL = "https://money-boost.000webhostapp.com/codigos/Pesquisa.php";
    private Map<String, String> params;

    public controlaVisao(String email,String datai,String dataf,String tipo, Response.Listener<String> listener) {
        super(Method.POST, PESQUISA_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("datainicio", datai);
        params.put("datafim", dataf);
        params.put("tipo", tipo);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

