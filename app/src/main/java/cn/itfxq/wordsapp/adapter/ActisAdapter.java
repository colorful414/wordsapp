package cn.itfxq.wordsapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.itfxq.wordsapp.R;
import cn.itfxq.wordsapp.model.Article;
import cn.itfxq.wordsapp.model.Words;
import cn.itfxq.wordsapp.util.ItFxqConstants;

public class ActisAdapter extends ArrayAdapter<Article> {
    private int resourceId;

    // 适配器的构造函数，把要适配的数据传入这里
    public ActisAdapter(Context context, int textViewResourceId, List<Article> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    // convertView 参数用于将之前加载好的布局进行缓存
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Article article=getItem(position); //获取当前项的Words实例

        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        View view;
        ViewHolder viewHolder;
        if (convertView==null){

            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder=new ViewHolder();
            viewHolder.ArticleImage=view.findViewById(R.id.acti_image);
            viewHolder.ArticleTitle=view.findViewById(R.id.acti_title);
            viewHolder.ArticleAuthor=view.findViewById(R.id.acti_author);
            viewHolder.ArticleCreateTime=view.findViewById(R.id.acti_createTime);

            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来

         String filePath= ItFxqConstants.PICPATH +article.getImageUrl();
         Bitmap bitmap = BitmapFactory.decodeFile(filePath, null);

        viewHolder.ArticleImage.setImageBitmap(bitmap);
        viewHolder.ArticleTitle.setText(article.getTitle());
        viewHolder.ArticleAuthor.setText(article.getUsername());
        viewHolder.ArticleCreateTime.setText(article.getCreateTime());
//        viewHolder.ArticleInfo.setText(article.getInfo());
        return view;
    }

    // 定义一个内部类，用于对控件的实例进行缓存
    class ViewHolder{
        ImageView ArticleImage;
        TextView ArticleTitle;
        TextView ArticleInfo;
        TextView ArticleAuthor;
        TextView ArticleCreateTime;
    }
}



