package cn.itfxq.wordsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.itfxq.wordsapp.MainActivity;
import cn.itfxq.wordsapp.R;
import cn.itfxq.wordsapp.util.DBUtils;
import cn.itfxq.wordsapp.util.ItFxqConstants;

public class LoginActivity extends AppCompatActivity {

    private TextView regTv;

    private Button loginBtn;
    private EditText loginNameEt ;
    private EditText loginPwdEt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //创建数据库 /data/data/cn.itfxq.wordsapp/database/xxx.db
        DBUtils dbUtils = new DBUtils(this, "itfxq-words.db", 1);
        SQLiteDatabase dataBase = dbUtils.getReadableDatabase();

        //注册
        regTv = findViewById(R.id.regTv);
        loginBtn=findViewById(R.id.loginBtn);
        loginNameEt = findViewById(R.id.log_name);
        loginPwdEt = findViewById(R.id.log_pwd);

        regTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(LoginActivity.this,RegActivity.class);
                //启动
                startActivity(intent);
            }
        });

    }
    //登录系统
    public void loginSys(View view){
        SQLiteDatabase db = DBUtils.getDbHelper(LoginActivity.this);
        String username = loginNameEt.getText().toString().trim();
        String pwd = loginPwdEt.getText().toString().trim();
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",pwd);

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"用户名和密码不能为空",Toast.LENGTH_SHORT).show();
        }else {
            if (DBUtils.checkUserIsExits(LoginActivity.this, username)) {
                //如果存在 就登录
                if (DBUtils.userLogin(LoginActivity.this,username,pwd)) {
                    Intent intent=new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    //启动
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "密码错误!", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "用户名不存在,请注册!", Toast.LENGTH_SHORT).show();
            }
        }

       
    }


}