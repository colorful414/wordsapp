package cn.itfxq.wordsapp.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cn.itfxq.wordsapp.model.Article;
import cn.itfxq.wordsapp.model.Words;
import cn.itfxq.wordsapp.util.CommonUtils;
import cn.itfxq.wordsapp.util.DBUtils;
import cn.itfxq.wordsapp.util.ItFxqConstants;

public class ArticleService {

    /**
     * 保存文章
     * @param article
     * @return
     */
    public boolean saveArticle(Context context, Article article){
        SQLiteDatabase dbHelper = DBUtils.getDbHelper(context);
        ContentValues values = new ContentValues();
        values.put("title",article.getTitle());
        values.put("info",article.getInfo());
        values.put("imageUrl",article.getImageUrl());
        values.put("username",DBUtils.getLoginUsername());
        values.put("createTime", CommonUtils.getCurrentTimeStr());
        try {
            long result = dbHelper.insert(ItFxqConstants.ARTICLE_TABLE,null,values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            dbHelper.close();
        }
    }


    public List<Article> queryAllArticles(Context context){
        List<Article> articleList = new ArrayList<>();
        SQLiteDatabase db = DBUtils.getDbHelper(context);
        String sql = "select * from "+ItFxqConstants.ARTICLE_TABLE+" order by id desc";
        Cursor cursor = db.rawQuery(sql, new String[]{});
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                Article article = new Article();
                Long id = cursor.getLong(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String info = cursor.getString(cursor.getColumnIndex("info"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
                String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
                article.setId(id);
                article.setTitle(title);
                article.setInfo(info);
                article.setImageUrl(imageUrl);
                article.setCreateTime(createTime);
                article.setUsername(username);
                articleList.add(article);
            }
            cursor.close();

        }else{
            cursor.close();

        }
        return articleList;
    }


    public Article queryArticleById(Context context,String artid){
       Article article = new Article();
        SQLiteDatabase db = DBUtils.getDbHelper(context);
        String sql = "select * from "+ItFxqConstants.ARTICLE_TABLE+" where id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{artid});
        if(cursor.getCount()>0){
              if(cursor.moveToNext()) {
                  Long id = cursor.getLong(cursor.getColumnIndex("id"));
                  String title = cursor.getString(cursor.getColumnIndex("title"));
                  String info = cursor.getString(cursor.getColumnIndex("info"));
                  String username = cursor.getString(cursor.getColumnIndex("username"));
                  String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
                  String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
                  article.setId(id);
                  article.setTitle(title);
                  article.setInfo(info);
                  article.setImageUrl(imageUrl);
                  article.setCreateTime(createTime);
                  article.setUsername(username);
              }
        }

        cursor.close();
        return article;
    }



}
