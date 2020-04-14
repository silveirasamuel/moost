package samuelpedro.moost.login;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import samuelpedro.moost.R;

public class Login extends AppCompatActivity {
    public static String name=null;
    public static float saldo= 0;
    public static String login=null;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(samuelpedro.moost.R.layout.activity_login);
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(R.drawable.icon);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Entrando...");
        progressDialog.setCancelable(false);

        final EditText email = (EditText) findViewById(samuelpedro.moost.R.id.loginUsuario);
        final EditText senha = (EditText) findViewById(samuelpedro.moost.R.id.senhaUsuario);
        final Button cadastro = (Button) findViewById(samuelpedro.moost.R.id.cadastrar);
        final Button entrar = (Button) findViewById(samuelpedro.moost.R.id.entrar);

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, samuelpedro.moost.cadastro.Cadastro.class);
                Login.this.startActivity(registerIntent);
            }
        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final String stringEmail = email.getText().toString();
                final String stringSenha = senha.getText().toString();
                System.out.println("Começouu");

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            System.out.println("Sucesso"+success);
                            if (success) {
                                name = jsonResponse.getString("nome");
                                login = jsonResponse.getString("email");
                                String aux = jsonResponse.getString("saldo");
                                saldo = Float.parseFloat(aux);
                                Intent intent = new Intent(Login.this, samuelpedro.moost.main.MainActivity.class);
                                Login.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                                progressDialog.cancel();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                controlaLogin loginRequest = new controlaLogin(stringEmail, stringSenha, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }
        });
        progressDialog.cancel();
    }
}
