package samuelpedro.moost.cadastro;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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

public class Cadastro extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(samuelpedro.moost.R.layout.activity_cadastro);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cadastrando...");
        progressDialog.setCancelable(false);
        final EditText Nome = (EditText) findViewById(samuelpedro.moost.R.id.nomecadastro);
        final EditText Login = (EditText) findViewById(samuelpedro.moost.R.id.logincadastro);
        final EditText Senha = (EditText) findViewById(samuelpedro.moost.R.id.senhacadastro);
        final Button bCadastrar = (Button) findViewById(samuelpedro.moost.R.id.cadastrar);

        bCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final String stringNome = Nome.getText().toString();
                final String stringLogin = Login.getText().toString();
                final String stringSenha = Senha.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String success = jsonResponse.getString("success");
                            System.out.println("aaaaaaaaaaaa"+success);
                            if (success.equals("ok")) {
                                Intent intent = new Intent(Cadastro.this, samuelpedro.moost.login.Login.class);
                                Cadastro.this.startActivity(intent);
                            }
                            if (success.equals("vazio")){
                                System.out.println("Clicouuu");
                                AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro.this);
                                builder.setMessage("Preencha todos os campos")
                                        .setNegativeButton("OK", null)
                                        .create()
                                        .show();
                                progressDialog.cancel();
                            }
                            if (success.equals("indisponivel")){
                                System.out.println("Clicouuu222");
                                AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro.this);
                                builder.setMessage("Email indisponivel")
                                        .setNegativeButton("OK", null)
                                        .create()
                                        .show();
                                progressDialog.cancel();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                controlaCadastro registerRequest = new controlaCadastro(stringNome, stringLogin, stringSenha, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Cadastro.this);
                queue.add(registerRequest);
            }
        });
        progressDialog.cancel();
    }
}