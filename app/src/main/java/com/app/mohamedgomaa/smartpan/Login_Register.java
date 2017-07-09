package com.app.mohamedgomaa.smartpan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
public class Login_Register extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
     GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;
    private static final int RC_SING_IN = 9001;
    private static final String TAG = "SingInActivity";
    EditText Login_ET_userName, Login_ET_password, Res_ET_name, Res_ET_user_name,
            Res_ET_password, Res_ET_mail, Res_ET_mobile, Forget_Et;
    LinearLayout Login_Layout, Reg_layout, Forget_layout;
    RadioGroup radio_Gender;
    RadioButton male;
    AlertDialog.Builder alerm, alerm2;
    CheckConnection_Internet checkInternet;
    String name, user_name, user_password, mail, mobile;
    int gender = 0;
    User_Info use_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login__regidster);
        ViewCompat.setLayoutDirection(findViewById(R.id.layer_id_login), ViewCompat.LAYOUT_DIRECTION_LTR);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        use_info=(User_Info)getApplicationContext();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton = (SignInButton) findViewById(R.id.btn_sign_in);
        Iniliazation();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new CheckConnection_Internet(getApplicationContext()).IsConnection()) {
                    singIn();
                }
                else
                {
                    Toast.makeText(Login_Register.this, "check Internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkInternet = new CheckConnection_Internet(this);
        radio_Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (R.id.male == checkedId) {
                    gender = 0;
                } else if (R.id.female == checkedId) {
                    gender = 1;
                }

            }
        });
    }
    private void singIn() {
        Intent singInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(singInIntent, RC_SING_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SING_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult : " + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            SharedPreferences user_store=getSharedPreferences("user",MODE_PRIVATE);
            SharedPreferences.Editor edit=user_store.edit();
            edit.putString("name",acct.getDisplayName());
            edit.putString("mail",acct.getEmail());
            edit.putString("mobile","");
            edit.apply();
            startActivity(new Intent(Login_Register.this, Main_Activity.class));
            finish();
        }

    }
    void getData_Reg() {
        name = Res_ET_name.getText().toString();
        user_name = Res_ET_user_name.getText().toString();
        user_password = Res_ET_password.getText().toString();
        mail = Res_ET_mail.getText().toString();
        mobile = Res_ET_mobile.getText().toString();
    }

    boolean CheckEmpty() {
        if (!name.equals("") && !user_name.equals("") && !user_password.equals("") && !mail.equals("") && !mobile.equals("")) {
            return true;
        } else
            Toast.makeText(this, "please insert all data", Toast.LENGTH_LONG).show();
        return false;
    }

    boolean checkEmail(String mail_string) {
        if (mail_string.contains("@") && mail_string.contains(".com")) {
            return true;
        }
        Res_ET_mail.setError("incorrect email");
        Forget_Et.setError("incorrect email");
        Toast.makeText(this, "incorrect email", Toast.LENGTH_LONG).show();
        return false;
    }

    boolean Checkpassword() {
        if (user_password.length() > 4) {
            return true;
        }
        Res_ET_password.setError("short password please insert more 4 character");
        Toast.makeText(this, "insert more 4 character", Toast.LENGTH_LONG).show();
        return false;
    }

    void Iniliazation() {
        //loginButton=(LoginButton)findViewById(R.id.id_login_btn_facebook);
        Login_ET_userName = (EditText) findViewById(R.id.id_login_user_name);
        Login_ET_password = (EditText) findViewById(R.id.id_login_user_password);
        Login_Layout = (LinearLayout) findViewById(R.id.Linear_Login);
        Reg_layout = (LinearLayout) findViewById(R.id.Linear_Register);
        Forget_layout = (LinearLayout) findViewById(R.id.Linear_Forget);
        Res_ET_name = (EditText) findViewById(R.id.id_Reg_name);
        Res_ET_user_name = (EditText) findViewById(R.id.id_Reg_user_name);
        Res_ET_password = (EditText) findViewById(R.id.id_Reg_password);
        Res_ET_mail = (EditText) findViewById(R.id.id_Reg_user_mail);
        Res_ET_mobile = (EditText) findViewById(R.id.id_Reg_user_mobile);
        Forget_Et = (EditText) findViewById(R.id.id_Forget_edt);
        male = (RadioButton) findViewById(R.id.male);
        radio_Gender = (RadioGroup) findViewById(R.id.radio_Gender);
        male.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alerm = new AlertDialog.Builder(this);
        alerm.setTitle("Exit");
        alerm.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        alerm.setMessage("sure,you want Exit app ?");
        alerm.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alerm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alerm.show();
    }

    public void ForgetPassword(View view) {
        Login_Layout.setVisibility(View.GONE);
        Reg_layout.setVisibility(View.GONE);
        Forget_layout.setVisibility(View.VISIBLE);
    }

    public void SignIn(View view) {
        Forget_layout.setVisibility(View.GONE);
        Login_Layout.setVisibility(View.GONE);
        Reg_layout.setVisibility(View.VISIBLE);
    }



    boolean CheckEmpty_login() {
        if (!Login_ET_userName.getText().toString().equals("") && !Login_ET_password.getText().toString().equals("")) {
            return true;
        } else
            Toast.makeText(this, "please insert all data", Toast.LENGTH_LONG).show();
        return false;
    }
    public void Login(View view) {
        if (new CheckConnection_Internet(this).IsConnection()) {
            showDialog();
            final AlertDialog ad = alerm.show();
            if (CheckEmpty_login()) {
                Singleton singleton_Login = new Singleton(this);
                StringRequest Result_Response = new StringRequest(Request.Method.POST,"https://zeafancom.000webhostapp.com/login.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ad.dismiss();
                        try {
                        JSONObject JsonObj=new JSONObject(response);
                        JSONArray JsonArry=JsonObj.getJSONArray("serverResponse_user_info");
                            String result=JsonArry.getJSONObject(0).getString("result");
                        if(result.equals("successful"))
                        {
                            Toast.makeText(Login_Register.this, "welcome", Toast.LENGTH_SHORT).show();
                            String name=JsonArry.getJSONObject(0).getString("name");
                            String mail=JsonArry.getJSONObject(0).getString("mail");
                            String mobile=JsonArry.getJSONObject(0).getString("mobile");
                            SharedPreferences user_store=getSharedPreferences("user",MODE_PRIVATE);
                            SharedPreferences.Editor edit=user_store.edit();
                            edit.putString("name",name);
                            edit.putString("mail",mail);
                            edit.putString("mobile",mobile);
                            edit.apply();
                            startActivity(new Intent(Login_Register.this,Main_Activity.class));
                            finish();
                        }
                        else
                        {
                            Login_ET_userName.setText("");
                            Login_ET_password.setText("");
                            Toast.makeText(Login_Register.this," user name or password is incorrect" , Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e)
                        {
                            Toast.makeText(Login_Register.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

            }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ad.dismiss();
                        Toast.makeText(Login_Register.this, "There Error connect with server", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                      String user_name_login  = Login_ET_userName.getText().toString();
                       String user_password_login = Login_ET_password.getText().toString();
                        Map<String, String> myMap2 = new HashMap<>();
                        myMap2.put("user_name",user_name_login);
                        myMap2.put("user_password",user_password_login);
                        return myMap2;
                    }
                };
                singleton_Login.addToRequestQueue(Result_Response);
        }
        }
    }
    void showDialog() {
        alerm = new AlertDialog.Builder(this);
        progressBar = new ProgressBar(this);
        alerm.setView(progressBar);
    }

    String url_register = "https://zeafancom.000webhostapp.com/register_user_smartPan.php";

    public void Sign_In_Reg(View view) {
        if (new CheckConnection_Internet(this).IsConnection()) {
            showDialog();
            final AlertDialog ad = alerm.show();
            getData_Reg();
            if (CheckEmpty() && Checkpassword() && checkEmail(mail)) {
                Singleton singleton = new Singleton(this);
                StringRequest Result_Response = new StringRequest(Request.Method.POST, url_register, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("successful")) {
                            ad.dismiss();
                            Toast.makeText(Login_Register.this, "Successful Register", Toast.LENGTH_SHORT).show();
                            backToLogin();
                        } else {
                            ad.dismiss();
                            Toast.makeText(Login_Register.this, "failure Register \n please Enter anther user name ", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ad.dismiss();
                        Toast.makeText(Login_Register.this, "There Error connect with server", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> myMap = new HashMap<>();
                        myMap.put("name", name);
                        myMap.put("user_name", user_name);
                        myMap.put("user_password", user_password);
                        myMap.put("mail", mail);
                        myMap.put("mobile", mobile);
                        myMap.put("gender", String.valueOf(gender));
                        return myMap;
                    }
                };
                singleton.addToRequestQueue(Result_Response);
            }
        } else {
            Toast.makeText(this, "check connect with Internet", Toast.LENGTH_LONG);
        }
    }

    public void Back(View view) {
        backToLogin();
    }

    void backToLogin() {
        Login_Layout.setVisibility(View.VISIBLE);
        Reg_layout.setVisibility(View.GONE);
        Forget_layout.setVisibility(View.GONE);
    }

    ProgressBar progressBar;
    String url_check_Email = "https://zeafancom.000webhostapp.com/check_email_SmartPan.php";

    public void Check_Email(View view) {
        if (new CheckConnection_Internet(this).IsConnection()) {
            alerm2 = new AlertDialog.Builder(this);
            progressBar = new ProgressBar(this);
            alerm2.setView(progressBar);
            final AlertDialog ad = alerm2.show();
            final String mail = Forget_Et.getText().toString();
            if (checkEmail(mail)) {
                Singleton singleton = new Singleton(this);
                StringRequest Result_Response = new StringRequest(Request.Method.POST, url_check_Email, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        if (response.equals("successful")) {
                            ad.dismiss();
                            Toast.makeText(Login_Register.this, "check your inbox mail", Toast.LENGTH_LONG).show();
                            backToLogin();
                        } else {
                            ad.dismiss();
                            Toast.makeText(Login_Register.this, "your email no exits", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ad.dismiss();
                        Toast.makeText(Login_Register.this, "There Error connect with server", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> myMap = new HashMap<>();
                        myMap.put("mail", mail);
                        return myMap;
                    }
                };
                singleton.addToRequestQueue(Result_Response);
            } else {
                ad.dismiss();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnecationFailed :" + connectionResult);
    }


}
