package com.example.zhangle.team1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import custfonts.MyEditText;

public class MainActivity extends Activity implements View.OnClickListener {


    //Login page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_employee_home);
        Button b = findViewById(R.id.signin1);
        b.setOnClickListener(this);
    }

    //get the input and return different home page depending on id.
    @Override
    public void onClick(View v) {
        MyEditText edit1 = findViewById(R.id.username);

        String name = edit1.getText().toString();

        new AsyncTask<String, Void, EmployeeItem>() {
            @Override
            protected EmployeeItem doInBackground(String... params) {
                return EmployeeItem.getId(params[0]);

            }
            @Override
            protected void onPostExecute(EmployeeItem result) {
                MyEditText edit2 = findViewById(R.id.password);
                String p = edit2.getText().toString();
                //logic for login
                if (result == null){
                    Toast.makeText(MainActivity.this, "Wrong UserName", Toast.LENGTH_LONG).show();
                }else{
                    String password = result.get("Password").toString();
                    if(p.equals(password)) {
                        Toast.makeText(MainActivity.this, "Login Succeed", Toast.LENGTH_LONG).show();
                        if (result.get("DepartmentID").equals("STORE")) {
                            Intent intent = new Intent(MainActivity.this, StoreHome.class);
                            intent.putExtra("cuser", String.valueOf(result.get("EmployeeName")));
                            CurrentUser.current = Integer.valueOf(result.get("EmployeeID"));
                            startActivity(intent);
                        }
                        else if (Integer.valueOf(result.get("RoleID"))==2 && result.get("Authorization").equals("Authorized")){
                            Intent intent = new Intent(MainActivity.this, EmployeeHeadHome.class);
                            intent.putExtra("cuser", String.valueOf(result.get("EmployeeName")));
                            CurrentUser.current = Integer.valueOf(result.get("EmployeeID"));
                            startActivity(intent);
                        }else if (Integer.valueOf(result.get("RoleID"))==1 && result.get("Authorization").equals("Representative")){
                            Intent intent = new Intent(MainActivity.this, EmployeeRepHome.class);
                            intent.putExtra("cuser", String.valueOf(result.get("EmployeeName")));
                            CurrentUser.current = Integer.valueOf(result.get("EmployeeID"));
                            CurrentUser.username = result.get("EmployeeName");
                            startActivity(intent);
                        }
                        else if (Integer.valueOf(result.get("RoleID"))==1 && result.get("Authorization").equals("Authorized")){
                            Intent intent = new Intent(MainActivity.this, EmployeeHeadHome.class);
                            intent.putExtra("cuser", String.valueOf(result.get("EmployeeName")));
                            CurrentUser.current = Integer.valueOf(result.get("EmployeeID"));
                            startActivity(intent);}
                        else{
                            Intent intent = new Intent(MainActivity.this, EmployeeHome.class);
                            intent.putExtra("cuser", String.valueOf(result.get("EmployeeName")));
                            CurrentUser.current = Integer.valueOf(result.get("EmployeeID"));
                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }.execute(name);

    }

}
