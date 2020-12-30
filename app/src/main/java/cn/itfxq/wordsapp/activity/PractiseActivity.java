package cn.itfxq.wordsapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import java.util.ArrayList;
import java.util.List;

import cn.itfxq.wordsapp.R;
import cn.itfxq.wordsapp.model.Words;
import cn.itfxq.wordsapp.service.PractiseService;
import cn.itfxq.wordsapp.util.ItFxqConstants;
import cn.itfxq.wordsapp.util.VoiceUtils;

public class PractiseActivity extends AppCompatActivity {

    private TextView mTextView;
    private TextView practise_numTv;
    private TextView practise_nameTv;
    private TextView practise_infoTv;
    private TextView practise_fyTv;
    private TextView practise_gdTv;
    private Button practise_nextBtn;
    private Integer count=1;
    private String tag = "PractiseActivity";
    private List<Words> mWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise);
        initViews();
        showBackBtn();
        loadData();
    }

    //点击下一个
    public void nextPractise(View view) {
        if(count > ItFxqConstants.RANDOMSIZE){
            Toast.makeText(this,"今天练习已完毕",Toast.LENGTH_SHORT).show();
        }else {
            loadData();
        }
    }


    //初始化试图
    private void initViews() {
        practise_numTv = findViewById(R.id.practise_num);
        practise_nameTv = findViewById(R.id.practise_name);
        practise_infoTv = findViewById(R.id.practise_info);
        practise_fyTv = findViewById(R.id.practise_fy);
        practise_gdTv = findViewById(R.id.practise_gd);
        practise_nextBtn = findViewById(R.id.practise_nextBtn);
    }
    //加载数据
    private void loadData(){
        if(count == 1){
            getRandomDatas();
        }
        //默认显示第一个列表数据
        Words words = mWords.get(count-1);
        practise_numTv.setText("第"+count+"题");
        practise_nameTv.setText(words.getName());
        practise_infoTv.setText(words.getInfo());
        practise_fyTv.setText(words.getFy());
        ++count;
    }
    //显示回退按钮
    private void showBackBtn(){
        //显示返回按钮
        if(NavUtils.getParentActivityName(PractiseActivity.this)!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    //获取所有列表数据
    private void getRandomDatas(){
        PractiseService practiseService = new PractiseService();
        //随机取5个单词 作为每日一练
        mWords =  practiseService.randomWords(this, ItFxqConstants.RANDOMSIZE);
    }

    //跟读单词
    public void gdBtn(View view){
        VoiceUtils.getInstance(getApplicationContext()).speakText(practise_nameTv.getText().toString().trim());
    }




}