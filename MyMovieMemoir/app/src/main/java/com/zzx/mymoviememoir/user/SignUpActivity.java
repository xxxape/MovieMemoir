package com.zzx.mymoviememoir.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zzx.mymoviememoir.R;
import com.zzx.mymoviememoir.server.Person;
import com.zzx.mymoviememoir.server.ReUseData;
import com.zzx.mymoviememoir.tools.MD5Utils;
import com.zzx.mymoviememoir.tools.NetworkConnection;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity implements View.OnFocusChangeListener{

    private NetworkConnection networkConnection;

    private EditText etEmail, etPwd, etRePwd, etFname, etLname, etDOB, etStNo, etStName, etPostcode;
    private TextView tvEmailHint, tvPwdHint, tvRePwdHint, tvFnameHint, tvLnameHint, tvDOBHint, tvStNoHint, tvStNameHint, tvPostcodeHint;
    private ImageView ivEmail, ivPwd, ivRePwd, ivFname, ivLname, ivDOB, ivStNo, ivStName, ivPostcode;
    private RadioGroup radioGender;
    private Calendar calendar;
    private Spinner stateSpinner;
    private Person person;
    private boolean emailFormat, emailReady, pwdReady, rePwdReady, fNameReady, lNameReady, DOBReady, stNoReady, stNameReady, postcodeReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        networkConnection = new NetworkConnection();
        emailFormat = false;
        emailReady = false;
        pwdReady = false;
        rePwdReady = false;
        fNameReady = false;
        lNameReady = false;
        DOBReady = false;
        stNoReady = false;
        stNameReady = false;
        postcodeReady = false;;

        // get the max id stored in the database
        GetMaxCredId getMaxCredId = new GetMaxCredId();
        getMaxCredId.execute();
        GetMaxPerId getMaxPerId = new GetMaxPerId();
        getMaxPerId.execute();

        person = new Person();

        // EditText
        etEmail = findViewById(R.id.etEmail2);
        etPwd = findViewById(R.id.etPwd);
        etRePwd = findViewById(R.id.etRePwd);
        etFname = findViewById(R.id.etFname);
        etLname = findViewById(R.id.etLname);
        etDOB = findViewById(R.id.etDOB);
        etStNo = findViewById(R.id.etStNo);
        etStName = findViewById(R.id.etStName);
        etPostcode = findViewById(R.id.etPostcode);

        // TextView
        tvEmailHint = findViewById(R.id.tvEmailHint);
        tvPwdHint = findViewById(R.id.tvPwdHint);
        tvRePwdHint = findViewById(R.id.tvRePwdHint);
        tvFnameHint = findViewById(R.id.tvFnameHint);
        tvLnameHint = findViewById(R.id.tvLnameHint);
        tvDOBHint = findViewById(R.id.tvDOBHint);
        tvStNoHint = findViewById(R.id.tvStNoHint);
        tvStNameHint = findViewById(R.id.tvStNameHint);
        tvPostcodeHint = findViewById(R.id.tvPostcodeHint);

        // ImageView
        ivEmail = findViewById(R.id.ivEmail);
        ivPwd = findViewById(R.id.ivPwd);
        ivRePwd = findViewById(R.id.ivRePwd);
        ivFname = findViewById(R.id.ivFname);
        ivLname = findViewById(R.id.ivLname);
        ivDOB = findViewById(R.id.ivDOB);
        ivStNo = findViewById(R.id.ivStNo);
        ivStName = findViewById(R.id.ivStName);
        ivPostcode = findViewById(R.id.ivPostcode);

        radioGender = findViewById(R.id.radioGender);
        calendar = Calendar.getInstance();
        stateSpinner = findViewById(R.id.stateSpinner);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        // verify email format
        etEmail.addTextChangedListener(new TextWatcher() {
            // do nothing
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = etEmail.getText().toString();
                Pattern p = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
                Matcher m = p.matcher(email);
                if (m.matches()) {
                    tvEmailHint.setText("");
                    emailFormat = true;
                } else {
                    tvEmailHint.setText("Please enter a valid Email!");
                    tvEmailHint.setTextColor(getResources().getColor(R.color.darkRed));
                    emailFormat = false;
                }
            }
            // do nothing
            @Override
            public void afterTextChanged(Editable s) { }
        });

        // verify is email exist
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    // get focus, do nothing
                } else {
                    // lose focus, check is empty first
                    if ("".equals(etEmail.getText().toString())) {
                        tvEmailHint.setText("Please enter a valid Email!");
                        tvEmailHint.setTextColor(getResources().getColor(R.color.darkRed));
                        ivEmail.setImageResource(R.drawable.ic_close_24px);
                        emailReady = false;
                    } else {
                        if (!emailFormat) {
                            tvEmailHint.setText("Please enter a valid Email!");
                            tvEmailHint.setTextColor(getResources().getColor(R.color.darkRed));
                            ivEmail.setImageResource(R.drawable.ic_close_24px);
                            emailFormat = false;
                        } else {
                            // email format is correct
                            // lose focus, check if email exist
                            IsEmailExist isEmailExist = new IsEmailExist();
                            isEmailExist.execute(etEmail.getText().toString());
                        }
                    }
                }
            }
        });

        etRePwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // has focus, do nothing
                } else {
                    // lose focus, check two passwords are the same
                    if ("".equals(etRePwd.getText().toString())) {
                        tvRePwdHint.setText("Please enter the confirm password!");
                        tvRePwdHint.setTextColor(getResources().getColor(R.color.darkRed));
                        ivRePwd.setImageResource(R.drawable.ic_close_24px);
                        rePwdReady = false;
                    } else if ("".equals(etPwd.getText().toString())) {
                        tvRePwdHint.setText("Please enter your password first!");
                        tvRePwdHint.setTextColor(getResources().getColor(R.color.darkRed));
                        ivRePwd.setImageResource(R.drawable.ic_close_24px);
                        rePwdReady = false;
                    } else if (!etPwd.getText().toString().equals(etRePwd.getText().toString())) {
                        tvRePwdHint.setText("Password does not match. Please enter again!");
                        tvRePwdHint.setTextColor(getResources().getColor(R.color.darkRed));
                        ivRePwd.setImageResource(R.drawable.ic_close_24px);
                        rePwdReady = false;
                    } else {
                        tvRePwdHint.setText("");
                        ivRePwd.setImageResource(R.drawable.ic_check_24px);
                        rePwdReady = true;
                    }
                }
            }
        });

        etPwd.setOnFocusChangeListener(this);
        etFname.setOnFocusChangeListener(this);
        etLname.setOnFocusChangeListener(this);
        etStNo.setOnFocusChangeListener(this);
        etStName.setOnFocusChangeListener(this);
        etPostcode.setOnFocusChangeListener(this);

        // select DOB
        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                etDOB.setText(new StringBuilder()
                                        .append(year)
                                        .append("-")
                                        .append((month + 1) < 10 ? "0" + (month + 1) : (month + 1))
                                        .append("-")
                                        .append((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth));
                            }
                        },
                        "".equals(etDOB.getText().toString())?calendar.get(Calendar.YEAR):Integer.valueOf(etDOB.getText().toString().substring(0,4)),
                        "".equals(etDOB.getText().toString())?calendar.get(Calendar.MONTH):Integer.valueOf(etDOB.getText().toString().substring(5,7))-1,
                        "".equals(etDOB.getText().toString())?calendar.get(Calendar.DAY_OF_MONTH):Integer.valueOf(etDOB.getText().toString().substring(8,10))
                );
                datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if ("".equals(etDOB.getText().toString())) {
                            tvDOBHint.setText("Please enter your date of birthday!");
                            tvDOBHint.setTextColor(getResources().getColor(R.color.darkRed));
                            ivDOB.setImageResource(R.drawable.ic_close_24px);
                            DOBReady = false;
                        } else {
                            tvDOBHint.setText("");
                            ivDOB.setImageResource(R.drawable.ic_check_24px);
                            DOBReady = true;
                        }
                    }
                });
                datePickerDialog.show();
            }
        });

        // click sign up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getWindow().getDecorView().findFocus() != null) {
                    getWindow().getDecorView().findFocus().clearFocus();
                }

                if (!emailReady || !pwdReady || !rePwdReady || !fNameReady || !lNameReady || !DOBReady || !stNoReady || !stNameReady || !postcodeReady) {
                    // not enter all the information
                    Toast.makeText(getApplicationContext(), "Please enter all the information correctly first!", Toast.LENGTH_SHORT).show();
                } else {
                    // all the information are correct
                    String passwordHash = MD5Utils.stringToMD5(etPwd.getText().toString());
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
                    currentDate = currentDate + "T00:00:00+08:00";
                    person.setCredid(ReUseData.getMaxCredId(), etEmail.getText().toString(), passwordHash, currentDate);

                    person.setPerid(ReUseData.getMaxPerId());
                    person.setPerfname(etFname.getText().toString());
                    person.setPerlname(etLname.getText().toString());
                    RadioButton curentButton = findViewById(radioGender.getCheckedRadioButtonId());
                    person.setPergender(curentButton.getText().toString());
                    String strDOB = etDOB.getText().toString() + "T00:00:00+08:00";
                    person.setPerdob(strDOB);
                    person.setPerstno(etStNo.getText().toString());
                    person.setPerstname(etStName.getText().toString());
                    person.setPerstate(stateSpinner.getSelectedItem().toString());
                    person.setPerpostcode(etPostcode.getText().toString());

                    AddCredentialTask addCredentialTask = new AddCredentialTask();
                    addCredentialTask.execute(person);
                }
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            // get focus, do nothing
        } else {
            // lose focus, get EditView id first
            switch (v.getId()) {
                case R.id.etPwd:
                    pwdReady= editTextIsEmpty(etPwd, tvPwdHint, ivPwd, "password");
                    break;
                case R.id.etFname:
                    fNameReady = editTextIsEmpty(etFname, tvFnameHint, ivFname, "first name");
                    break;
                case R.id.etLname:
                    lNameReady = editTextIsEmpty(etLname, tvLnameHint, ivLname, "last name");
                    break;
                case R.id.etStNo:
                    stNoReady = editTextIsEmpty(etStNo, tvStNoHint, ivStNo, "street number");
                    break;
                case R.id.etStName:
                    stNameReady = editTextIsEmpty(etStName, tvStNameHint, ivStName, "street name");
                    break;
                case R.id.etPostcode:
                   postcodeReady =  editTextIsEmpty(etPostcode, tvPostcodeHint, ivPostcode, "postcode");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * check EditText is empty
     * @param et
     * @param tv
     * @param iv
     */
    private boolean editTextIsEmpty(EditText et, TextView tv, ImageView iv, String hint) {
        boolean isReady  =false;
        if ("".equals(et.getText().toString())) {
            tv.setText("Please enter your " + hint + "!");
            tv.setTextColor(getResources().getColor(R.color.darkRed));
            iv.setImageResource(R.drawable.ic_close_24px);
            isReady = false;
        } else {
            tv.setText("");
            iv.setImageResource(R.drawable.ic_check_24px);
            isReady = true;
        }
        return isReady;
    }

    /**
     *  get max credential id to manage id
     */
    private class GetMaxCredId extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return networkConnection.getMaxCredId();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            ReUseData.setMaxCredId(integer);
        }
    }

    /**
     *  get max person id to manage id
     */
    private class GetMaxPerId extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return networkConnection.getMaxPersonId();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            ReUseData.setMaxPerId(integer);
        }
    }

    /**
     * add credential task
     */
    private class AddCredentialTask extends AsyncTask<Person, Void, Integer> {

        @Override
        protected Integer doInBackground(Person... people) {
            return networkConnection.addCredential(people[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == 204) {
                AddPersonTask addPersonTask = new AddPersonTask();
                addPersonTask.execute(person);
            } else {
                Toast.makeText(getApplicationContext(), "Sign up unsuccessfully, please try again later!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * add person task
     */
    private class AddPersonTask extends AsyncTask<Person, Void, Integer> {

        @Override
        protected Integer doInBackground(Person... people) {
            return networkConnection.addPerson(people[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == 204) {
                // go back to homepage to sign in
                Toast.makeText(getApplicationContext(), "Sign up successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Sign up unsuccessfully, please try again later!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class IsEmailExist extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.isEmailExist(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if (!"[]".equals(s)) {
                // exist
                tvEmailHint.setText("Your email has been registered. Please return to sign in or enter a new email!");
                tvEmailHint.setTextColor(getResources().getColor(R.color.darkRed));
                ivEmail.setImageResource(R.drawable.ic_close_24px);
                emailReady = false;
            } else {
                // not exist
                tvEmailHint.setText("");
                ivEmail.setImageResource(R.drawable.ic_check_24px);
                emailReady = true;
            }
        }
    }
}
