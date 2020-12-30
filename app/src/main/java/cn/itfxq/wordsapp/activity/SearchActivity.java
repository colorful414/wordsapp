package cn.itfxq.wordsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.itfxq.wordsapp.R;
import cn.itfxq.wordsapp.model.Words;
import cn.itfxq.wordsapp.service.WordsService;

/**
 * @description: SearchActivity
 * @author:marker
 * @copyright:www.itfxq.cn
 * @email:2579692606@qq.com
 * @createTime 2020/11/20 14:34
 */
public class SearchActivity extends AppCompatActivity {

    private String tag = "SearchActivity";

    private EditText translate_enEt;
    private TextView translate_showcnTv;


    private EditText translate_cnEt;
    private TextView translate_showenTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }


    private void initView() {
        translate_enEt = findViewById(R.id.translate_en);
        translate_cnEt = findViewById(R.id.translate_cn);
        translate_showenTv = findViewById(R.id.translate_showen);
        translate_showcnTv = findViewById(R.id.translate_showcn);
    }

    //从中文到英文翻译
    public void translateCnToEn(View view){
        String cn = translate_cnEt.getText().toString().trim();
        if(TextUtils.isEmpty(cn)){
            Toast.makeText(this,"输入的内容为空",Toast.LENGTH_SHORT).show();;
        }else{
            WordsService wordsService = new WordsService();
            Words words = wordsService.queryWordsByCn(this, cn);
            if(words == null){
                translate_showenTv.setText("抱歉,词库里面没有收录该单词");
            }else {
                translate_showenTv.setText(words.getName());
            }
        }
    }

    //从英文到中翻译
    public void translateEnToCn(View view){
        String en = translate_enEt.getText().toString().trim();
        if(TextUtils.isEmpty(en)){
            Toast.makeText(this,"输入的内容为空",Toast.LENGTH_SHORT).show();;
        }else{
            WordsService wordsService = new WordsService();
            Words words = wordsService.queryWordsByEn(this, en);
            if(words == null){
                translate_showcnTv.setText("抱歉,词库里面没有收录该词的解释");
            }else {
                translate_showcnTv.setText(words.getInfo());
            }
        }
    }
}