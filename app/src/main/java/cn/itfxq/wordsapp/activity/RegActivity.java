package cn.itfxq.wordsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import cn.itfxq.wordsapp.R;
import cn.itfxq.wordsapp.util.DBUtils;
import cn.itfxq.wordsapp.util.ItFxqConstants;

public class RegActivity extends AppCompatActivity {

    private TextView goLoginTv ;
    private EditText usernameEt ;
    private EditText pwdEt ;
    private EditText emailEt ;
    private EditText telEt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        usernameEt = findViewById(R.id.username);
        pwdEt = findViewById(R.id.pwd);
        emailEt = findViewById(R.id.email);
        telEt = findViewById(R.id.tel);

        goLoginTv = findViewById(R.id.goLoginTv);
        goLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void saveUser(View view){
        DBUtils dbUtils = new DBUtils(this, ItFxqConstants.DBNAME, 1);
        SQLiteDatabase db = dbUtils.getReadableDatabase();
        ContentValues values = new ContentValues();
        String username = usernameEt.getText().toString().trim();
        String pwd = pwdEt.getText().toString().trim();
        String tel = telEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"用户名和密码不能为空",Toast.LENGTH_SHORT).show();
        }else{
            //如果已经存在 就提示
            if(DBUtils.checkUserIsExits(this,username)){
                Toast.makeText(this,"用户名已被注册",Toast.LENGTH_SHORT).show();
            }else{
                values.put("username",username);
                values.put("password",pwdEt.getText().toString());
                values.put("tel",telEt.getText().toString());
                values.put("email",emailEt.getText().toString());
                long result = db.insert(ItFxqConstants.USER_TABLE,null,values);
                db.close();
                if(result != -1){
                    AlertDialog.Builder dlog = new AlertDialog.Builder(this);
                    dlog.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dlg,int arg1) {
                            Intent intent = new Intent(RegActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    dlog.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dlg,int arg1) {
                            dlg.dismiss();;
                        }
                    });
                    dlog.setMessage("保存成功,返回登录。");
                    dlog.setTitle("温馨提示");
                    dlog.show();

                }
            }
        }



    }


}