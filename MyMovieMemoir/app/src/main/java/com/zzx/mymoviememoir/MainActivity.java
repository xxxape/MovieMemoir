package com.zzx.mymoviememoir;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zzx.mymoviememoir.tools.MD5Utils;
import com.zzx.mymoviememoir.tools.NetworkConnection;
import com.zzx.mymoviememoir.user.SignUpActivity;
import com.zzx.mymoviememoir.user.UserInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    private NetworkConnection networkConnection;

    private EditText etEmail;
    private EditText etPassword;
    private Button btnSignIn;
    private ImageView ivVisible;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkConnection = new NetworkConnection();

        // get widget
        etEmail = findViewById(R.id.etEmail1);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        ivVisible = findViewById(R.id.ivVisible);
        tvSignUp = findViewById(R.id.tv_sign_up);

        // set button click listener
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etEmail.getText().toString();
                String password = MD5Utils.stringToMD5(etPassword.getText().toString());
                CheckUser checkUser = new CheckUser();

                if (!username.isEmpty() && !password.isEmpty())
                    checkUser.execute(username, password);
                else
                    Toast.makeText(v.getContext(), "Please enter the username and password first!", Toast.LENGTH_SHORT).show();
            }
        });

        // set password is visible image
        ivVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if not visible
                if (etPassword.getInputType() == (InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT)) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivVisible.setImageResource(R.drawable.ic_visibility_24px);
                }
                // if visible
                else if (etPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                    ivVisible.setImageResource(R.drawable.ic_visibility_off_24px);
                }
            }
        });

        // sign up button
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * check user by username and password
     */
    private class CheckUser extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.checkUser(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(String s) {
            if (!"".equals(s)) {
                GetPeridAndFname getPeridAndFname = new GetPeridAndFname();
                getPeridAndFname.execute(s);
            } else {
                Toast.makeText(getApplicationContext(), "Wrong username or password. Please try again!", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * get person id and first name by credential id and send to MainScreenActivity
     */
    private class GetPeridAndFname extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            // get person info by credId
            return networkConnection.getPersonById(strings[0], "1");
        }

        @Override
        protected void onPostExecute(String s) {
            if (!"".equals(s)) {
                // get id and first name, send to the MainScreenActivity
                JsonObject person = new JsonParser().parse(s).getAsJsonObject();
                String perId = person.get("perid").getAsString();
                String perFname = person.get("perfname").getAsString();
                Intent intent = new Intent(MainActivity.this, MainScreenActivity.class);
                intent.putExtra("flag", 0);
                UserInfo.setPerId(perId);
                UserInfo.setPerFname(perFname);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Login failed. Please try again!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
