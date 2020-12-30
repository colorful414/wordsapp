package cn.itfxq.wordsapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import cn.itfxq.wordsapp.R;
import cn.itfxq.wordsapp.model.Article;
import cn.itfxq.wordsapp.service.ArticleService;
import cn.itfxq.wordsapp.util.ItFxqConstants;

public class ArticleDetailActivity extends AppCompatActivity {

    private ImageView arti_detail_imgUrl;
    private TextView arti_detail_title;
    private TextView arti_detail_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        initView();

        String artid =  getIntent().getStringExtra("artid");
        ArticleService articleService = new ArticleService();
        Article article = articleService.queryArticleById(this, artid);
        //设置展示
        Bitmap bitmap = BitmapFactory.decodeFile(ItFxqConstants.PICPATH+article.getImageUrl(), null);
        arti_detail_imgUrl.setImageBitmap(bitmap);
        arti_detail_title.setText(article.getTitle());
        arti_detail_info.setText(article.getInfo());

        //显示返回按钮
        if(NavUtils.getParentActivityName(ArticleDetailActivity.this)!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        arti_detail_imgUrl = findViewById(R.id.arti_detail_imgUrl);
        arti_detail_title = findViewById(R.id.arti_detail_title);
        arti_detail_info = findViewById(R.id.arti_detail_info);
    }


}