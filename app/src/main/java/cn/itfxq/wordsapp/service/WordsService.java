package cn.itfxq.wordsapp.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.itfxq.wordsapp.model.Words;
import cn.itfxq.wordsapp.util.DBUtils;
import cn.itfxq.wordsapp.util.ItFxqConstants;

public class WordsService {

    /**
     * 保存单词
     * @param words
     * @return
     */
    public boolean saveWords(Context context, Words words){
        SQLiteDatabase dbHelper = DBUtils.getDbHelper(context);
        ContentValues values = new ContentValues();
        values.put("name",words.getName());
        values.put("info",words.getInfo());
        values.put("category",words.getCategory());
        values.put("imageUrl",words.getImageUrl());
        values.put("fy",words.getFy());
        try {
            long result = dbHelper.insert(ItFxqConstants.WORD_TABLE,null,values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            dbHelper.close();
        }
    }

    /**
     * 判断单词是否已经存在
     */
    public  boolean checkWordsIsExits(Context context,String name){
        SQLiteDatabase db = DBUtils.getDbHelper(context);
        String sql = "select * from "+ItFxqConstants.WORD_TABLE+" where name=?";
        Cursor cursor = db.rawQuery(sql, new String[]{name});
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    /**
     * 翻译 通过英文查询查询中文
     */
    public Words queryWordsByEn(Context context, String en){
        Words word = null;
        SQLiteDatabase db = DBUtils.getDbHelper(context);
        String sql = "select * from "+ItFxqConstants.WORD_TABLE+" where name=?";
        Cursor cursor = db.rawQuery(sql, new String[]{en});
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                word = new Words();
                Long id = cursor.getLong(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String info = cursor.getString(cursor.getColumnIndex("info"));
                String fy = cursor.getString(cursor.getColumnIndex("fy"));
                String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
                String cg = cursor.getString(cursor.getColumnIndex("category"));
                word.setId(id);
                word.setName(name);
                word.setCategory(cg);
                word.setInfo(info);
                word.setImageUrl(imageUrl);
                word.setFy(fy);
            }
        }
        cursor.close();
        return word;
    }


    /**
     * 翻译 通过中文查询查询英文
     */
    public Words queryWordsByCn(Context context, String cn){
        Words word = null;
        SQLiteDatabase db = DBUtils.getDbHelper(context);
            String sql = "select * from "+ItFxqConstants.WORD_TABLE+" where info like ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%"+cn+"%"});
        if(cursor.getCount()>0){
            if(cursor.moveToNext()){
                word = new Words();
                Long id = cursor.getLong(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String info = cursor.getString(cursor.getColumnIndex("info"));
                String fy = cursor.getString(cursor.getColumnIndex("fy"));
                String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
                String cg = cursor.getString(cursor.getColumnIndex("category"));
                word.setId(id);
                word.setName(name);
                word.setCategory(cg);
                word.setInfo(info);
                word.setImageUrl(imageUrl);
                word.setFy(fy);
            }
        }
        cursor.close();
        return word;
    }

    /**
     * 根据类别查询单词
     */
    public List<Words> queryWordsByCategory(Context context, String category){
        List<Words> wordsList = new ArrayList<>();
        SQLiteDatabase db = DBUtils.getDbHelper(context);
        String sql = "select * from "+ItFxqConstants.WORD_TABLE+" where category=?";
        Cursor cursor = db.rawQuery(sql, new String[]{category});
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                Words word = new Words();
                Long id = cursor.getLong(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String info = cursor.getString(cursor.getColumnIndex("info"));
                String fy = cursor.getString(cursor.getColumnIndex("fy"));
                String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
                String cg = cursor.getString(cursor.getColumnIndex("category"));
                word.setId(id);
                word.setName(name);
                word.setCategory(cg);
                word.setInfo(info);
                word.setImageUrl(imageUrl);
                word.setFy(fy);
                wordsList.add(word);
            }
            cursor.close();

        }else{
            cursor.close();

        }
        return wordsList;
    }



    /**
     * 查询所有单词
     */
    public List<Words> queryAllWords(Context context){
        List<Words> wordsList = new ArrayList<>();
        SQLiteDatabase db = DBUtils.getDbHelper(context);
        String sql = "select * from "+ItFxqConstants.WORD_TABLE;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                Words word = new Words();
                Long id = cursor.getLong(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String info = cursor.getString(cursor.getColumnIndex("info"));
                String fy = cursor.getString(cursor.getColumnIndex("fy"));
                String imageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"));
                String cg = cursor.getString(cursor.getColumnIndex("category"));
                word.setId(id);
                word.setName(name);
                word.setCategory(cg);
                word.setInfo(info);
                word.setImageUrl(imageUrl);
                word.setFy(fy);
                wordsList.add(word);
            }
            cursor.close();

        }else{
            cursor.close();

        }
        return wordsList;
    }




}
