package cn.itfxq.wordsapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.itfxq.wordsapp.R;
import cn.itfxq.wordsapp.adapter.WordsAdapter;
import cn.itfxq.wordsapp.model.Words;
import cn.itfxq.wordsapp.service.WordsService;

public class DicListActivity extends AppCompatActivity {

    //创建ListView 存储数据
    private List<Words> mWordsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dic_list);

        //显示返回按钮
        if(NavUtils.getParentActivityName(DicListActivity.this)!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //初始化数据
        initWords();
        WordsAdapter adapter=new WordsAdapter(DicListActivity.this,R.layout.diclist_item,mWordsList);

        // 将适配器上的数据传递给listView
        ListView listView=findViewById(R.id.dictListViewId);
        listView.setAdapter(adapter);

        // 为ListView注册一个监听器，当用户点击了ListView中的任何一个子项时，就会回调onItemClick()方法
        // 在这个方法中可以通过position参数判断出用户点击的是那一个子项
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Words Words=mWordsList.get(position);
                Toast.makeText(DicListActivity.this,Words.getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWords() {
        String category = getIntent().getStringExtra("category");
        WordsService wordsService = new WordsService();
        mWordsList =  wordsService.queryWordsByCategory(getBaseContext(),category);

    }
}