package cn.itfxq.wordsapp.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.io.File;

import cn.itfxq.wordsapp.MainActivity;
import cn.itfxq.wordsapp.R;
import cn.itfxq.wordsapp.model.Article;
import cn.itfxq.wordsapp.model.Words;
import cn.itfxq.wordsapp.service.ArticleService;
import cn.itfxq.wordsapp.service.WordsService;
import cn.itfxq.wordsapp.util.CommonUtils;
import cn.itfxq.wordsapp.util.ItFxqConstants;

public class AddFragment extends Fragment {

    private static final String[] categoryList={"动物词库","人物词库","水果词库","车子词库","两性词库","食物词库"};

    private Spinner categorySpinner;
    private ArrayAdapter<String> adapter;
    private Button openPicDialog;
    private Button openActicleDialog;
    private TextView closePicTv;
    private TextView openPhotoTv;
    private TextView takePhotoTv;
    private ImageView wordPicIv;
    private ImageView actPicIv;

    private View filelayout;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;

    private Button fb_saveWords;
    private EditText fb_words_name;
    private EditText fb_words_info;
    private EditText fb_words_fy;
    private Spinner dicCategory;

    private Button fb_saveArticle;
    private EditText fb_article_title;
    private EditText fb_article_info;

    private String imageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        //tabs选项卡
        TabHost tabHost = view.findViewById(R.id.tabhost);
        tabHost.setup();

        inflater.inflate(R.layout.activity_add_words,tabHost.getTabContentView());
        inflater.inflate(R.layout.activity_add_acticle,tabHost.getTabContentView());

        TabWidget tabWidget = tabHost.getTabWidget();
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("发布单词")
                .setContent(R.id.addWordsId));
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("投递文章")
                .setContent(R.id.addActicleId));

        for (int i =0; i < tabWidget.getChildCount(); i++) {
            //修改Tabhost高度和宽度
            tabWidget.getChildAt(i).getLayoutParams().height = 100;
            tabWidget.getChildAt(i).getLayoutParams().width = 65;
            //修改显示字体大小
            TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(25);

        }
        tabHost.setCurrentTab(0);

        //加载数据
        categorySpinner = (Spinner) view.findViewById(R.id.dicCategory);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,categoryList);
        //将adapter 添加到spinner中
        categorySpinner.setAdapter(adapter);

        //初始化组件
        initViews(view);

        return view;
    }

    public void  initViews(View view){
        openPicDialog = view.findViewById(R.id.openPicDialog);
        openActicleDialog = view.findViewById(R.id.openActicleDialog);
        wordPicIv = view.findViewById(R.id.wordPicIv);
        actPicIv = view.findViewById(R.id.actPicIv);
        fb_words_name = view.findViewById(R.id.fb_words_name);
        fb_words_info = view.findViewById(R.id.fb_words_info);
        fb_words_fy = view.findViewById(R.id.fb_words_fy);
        fb_saveWords = view.findViewById(R.id.fb_saveWords);

        fb_article_title = view.findViewById(R.id.fb_article_title);
        fb_article_info = view.findViewById(R.id.fb_article_info);
        fb_saveArticle = view.findViewById(R.id.fb_saveArticle);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        bindEvent();
    }

    private void bindEvent(){
        openPicDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建对话框
                builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                filelayout = inflater.inflate(R.layout.dict_selectfile, null);//获取自定义布局
                //设置对话框布局
                builder.setView(filelayout);
                //生成对话框
                dialog = builder.create();
                dialog.show();

                //绑定关闭事件
                closePicTv = filelayout.findViewById(R.id.closeDig);
                closePicTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //关闭对话框
                        dialog.dismiss();
                    }
                });
                //绑定打开相册事件
                openPhotoTv = filelayout.findViewById(R.id.openPhoto);
                openPhotoTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    7);
                        } else {

                            openCloosePic();
                        }
                        dialog.dismiss();
                    }
                });

            }
        });
        //文章
        openActicleDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建对话框
                builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                filelayout = inflater.inflate(R.layout.dict_selectfile, null);//获取自定义布局
                //设置对话框布局
                builder.setView(filelayout);
                //生成对话框
                dialog = builder.create();
                dialog.show();

                //绑定关闭事件
                closePicTv = filelayout.findViewById(R.id.closeDig);
                closePicTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //关闭对话框
                        dialog.dismiss();
                    }
                });
                //绑定打开相册事件
                openPhotoTv = filelayout.findViewById(R.id.openPhoto);
                openPhotoTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    7);
                        } else {

                            openActCloosePic();
                        }
                        dialog.dismiss();
                    }
                });

            }
        });
        //保存单词事件
        fb_saveWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Words words = getSaveWordsData();
                WordsService wordsService = new WordsService();
                boolean isExits = wordsService.checkWordsIsExits(getContext(), words.getName());
                if(!isExits){
                    boolean isResult = wordsService.saveWords(getContext(), words);
                    if(isResult){
                        clearEditData();
                        Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"保存失败",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"单词已存在,无需添加",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //保存文章事件
        fb_saveArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Article article = getSaveArticleData();
                ArticleService articleService = new ArticleService();
                boolean isSuccess = articleService.saveArticle(getContext(), article);
                if(isSuccess){
                    clearArticleEditData();
                    Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(getActivity(),MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(),"保存失败",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //获取保存的文章
    private Article getSaveArticleData(){
        Article article = new Article();
        String articleTitle = fb_article_title.getText().toString();
        String articleInfo = fb_article_info.getText().toString();
        article.setTitle(articleTitle);
        article.setInfo(articleInfo);
        article.setImageUrl(imageUrl);
        return article;
    }

    //获取保存的数据
    private Words getSaveWordsData(){
        Words words = new Words();
        String wordName = fb_words_name.getText().toString();
        String wordInfo = fb_words_info.getText().toString();
        String wordFy = fb_words_fy.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();
        words.setName(wordName);
        words.setInfo(wordInfo);
        words.setImageUrl(imageUrl);
        words.setFy(wordFy);
        words.setCategory(category);
        return words;
    }
    //清空数据
    private void clearEditData(){
        fb_words_name.setText("");
        fb_words_info.setText("");
        fb_words_fy.setText("");
        fb_words_fy.setText("");
        wordPicIv.setImageResource(0);
    }
    //clearArticleEditData清理文章数据
    private void clearArticleEditData(){
        fb_article_title.setText("");
        fb_article_info.setText("");
        actPicIv.setImageResource(0);
    }


    private void openCloosePic() {
        //打开相册
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 2);

    }

    private void openActCloosePic() {
        //打开相册
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 3);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == Activity.RESULT_OK
                && null != data) {
            try {
                //获取路径
                Uri selectedImage = data.getData();
                Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                Tiny.getInstance().source(selectedImage).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                    @Override
                    public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                        if(requestCode == 2) {
                            savePicAndView(bitmap, outfile,selectedImage);
                        }else{
                            saveActPicAndView(bitmap, outfile,selectedImage);
                        }
                    }
                });
            } catch (Exception e) {
                //"上传失败");
            }
        }
    }


    private void savePicAndView(Bitmap bitmap, String outfile,Uri selectedImage) {
        bitmap = CommonUtils.getBitmapFromUri(getContext(),selectedImage);
        imageUrl = CommonUtils.saveBitmapToFile(bitmap);
        wordPicIv.setImageBitmap(bitmap);

    }

    private void saveActPicAndView( Bitmap bitmap, String outfile,Uri selectedImage) {
//        File file = new File(outfile);
//        imageUrl = outfile.replace(ItFxqConstants.PICPATH,"");
//        actPicIv.setImageBitmap(bitmap);

        bitmap = CommonUtils.getBitmapFromUri(getContext(),selectedImage);
        imageUrl = CommonUtils.saveBitmapToFile(bitmap);
        actPicIv.setImageBitmap(bitmap);
    }

}