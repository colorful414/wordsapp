package cn.itfxq.wordsapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import cn.itfxq.wordsapp.R;
import cn.itfxq.wordsapp.model.Words;
import cn.itfxq.wordsapp.util.ItFxqConstants;

public class WordsAdapter extends ArrayAdapter<Words> {

    private int resourceId;

    // 适配器的构造函数，把要适配的数据传入这里
    public WordsAdapter(Context context, int textViewResourceId, List<Words> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    // convertView 参数用于将之前加载好的布局进行缓存
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Words Words=getItem(position); //获取当前项的Words实例
        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        View view;
        ViewHolder viewHolder;
        if (convertView==null){

            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder=new ViewHolder();
            viewHolder.WordsImage=view.findViewById(R.id.word_image);
            viewHolder.WordsName=view.findViewById(R.id.word_name);
            viewHolder.WordsInfo=view.findViewById(R.id.word_info);

            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来

         String filePath= ItFxqConstants.PICPATH +Words.getImageUrl();
         Bitmap bitmap = BitmapFactory.decodeFile(filePath, null);

        viewHolder.WordsImage.setImageBitmap(bitmap);
        viewHolder.WordsName.setText(Words.getName());
        viewHolder.WordsInfo.setText(Words.getInfo());
        return view;
    }

    // 定义一个内部类，用于对控件的实例进行缓存
    class ViewHolder{
        ImageView WordsImage;
        TextView WordsName;
        TextView WordsInfo;
    }
}



