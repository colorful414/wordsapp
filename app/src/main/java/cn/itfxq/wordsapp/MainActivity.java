package cn.itfxq.wordsapp;



import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import cn.itfxq.wordsapp.fragment.AddFragment;
import cn.itfxq.wordsapp.fragment.HomeFragment;
import cn.itfxq.wordsapp.fragment.MyFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //UI Object

    private TextView txt_home;
    private TextView txt_add;
    private TextView txt_my;
    private FrameLayout ly_content;

    //Fragment Object
    private HomeFragment fg1;
    private AddFragment fg2;
    private MyFragment fg3;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        bindViews();
        txt_home.performClick();   //模拟一次点击，既进去后选择第一项
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        txt_home = (TextView) findViewById(R.id.home_conent);
        txt_add = (TextView) findViewById(R.id.add_conent);
        txt_my = (TextView) findViewById(R.id.my_conent);;
        ly_content = (FrameLayout) findViewById(R.id.ly_content);

        txt_home.setOnClickListener(this);
        txt_add.setOnClickListener(this);
        txt_my.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        txt_home.setSelected(false);
        txt_add.setSelected(false);
        txt_my.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.home_conent:
                setSelected();
                txt_home.setSelected(true);
                if(fg1 == null){
                    fg1 = new HomeFragment();
                    fTransaction.add(R.id.ly_content,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.add_conent:
                setSelected();
                txt_add.setSelected(true);
                if(fg2 == null){
                    fg2 = new AddFragment();
                    fTransaction.add(R.id.ly_content,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;
            case R.id.my_conent:
                setSelected();
                txt_my.setSelected(true);
                if(fg3 == null){
                    fg3 = new MyFragment();
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;

        }
        fTransaction.commit();
    }
}