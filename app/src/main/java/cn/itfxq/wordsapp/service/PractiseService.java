package cn.itfxq.wordsapp.service;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import cn.itfxq.wordsapp.model.Words;

/**
 * @description: PractiseService
 * @author:marker
 * @copyright:www.itfxq.cn
 * @email:2579692606@qq.com
 * @createTime 2020/11/20 14:34
 */
public class PractiseService {

    /**
     * 随机取出5个 每一练习
     * @return
     */
    public List<Words> randomWords(Context context,Integer rsize){

        //查询所有单词
        WordsService wordsService = new WordsService();
        List<Words> words = wordsService.queryAllWords(context);
        //取出所有的ids (11 26 30 89 90 10 90)
        List<Long> ids = words.stream().map(word -> {
            return word.getId();
        }).collect(Collectors.toList());
        //目标数组
        List<Long> resultIds = new ArrayList();
        for(int i=0;i<rsize;i++) {
            Long target = ids.get(new Random().nextInt(ids.size()));
            resultIds.add(target);
            ids.remove(target);
        }

        /**
         * 11 88 99 10 123  11
         * 88 99 10
         */
        List<Words> rWords = new ArrayList<>();
        words.forEach(word->{
            resultIds.forEach(id->{
                if(word.getId() == id){
                    rWords.add(word);
                }
            });
        });
        return rWords;

    }
}
