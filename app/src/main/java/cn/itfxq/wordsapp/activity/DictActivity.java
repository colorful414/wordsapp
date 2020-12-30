package cn.itfxq.wordsapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.itfxq.wordsapp.R;
import cn.itfxq.wordsapp.fragment.AddFragment;
import cn.itfxq.wordsapp.fragment.HomeFragment;
import cn.itfxq.wordsapp.fragment.MyFragment;

public class DictActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);
    }

    public void onDictClick(View v){

        Intent intent = new Intent(DictActivity.this,DicListActivity.class);
        switch (v.getId()){
            case R.id.animalBtn:
                intent.putExtra("category","动物词库");
                startActivity(intent);
                break;
            case R.id.personBtn:
                intent.putExtra("category","人物词库");
                startActivity(intent);
                break;
            case R.id.FruitBtn:
                intent.putExtra("category","水果词库");
                startActivity(intent);
                break;
            case R.id.SexBtn:
                intent.putExtra("category","两性词库");
                startActivity(intent);
                break;
            case R.id.FoodBtn:
                intent.putExtra("category","食物词库");
                startActivity(intent);
                break;
            case R.id.CarBtn:
                intent.putExtra("category","车子词库");
                startActivity(intent);
                break;

        }
    }
}