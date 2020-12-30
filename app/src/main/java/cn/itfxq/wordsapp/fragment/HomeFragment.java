package cn.itfxq.wordsapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.itfxq.wordsapp.R;
import cn.itfxq.wordsapp.activity.ArticleDetailActivity;
import cn.itfxq.wordsapp.activity.DictActivity;
import cn.itfxq.wordsapp.activity.PractiseActivity;
import cn.itfxq.wordsapp.activity.SearchActivity;
import cn.itfxq.wordsapp.adapter.ActisAdapter;
import cn.itfxq.wordsapp.adapter.LooperPagerAdapter;
import cn.itfxq.wordsapp.model.Article;
import cn.itfxq.wordsapp.service.ArticleService;
import cn.itfxq.wordsapp.util.CommonUtils;


public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private ViewPager mLooperPager;

    private LooperPagerAdapter mLooperPagerAdapter;

    private Handler mHandler = null;

    private boolean mIsTouch=false;

    private LinearLayout pointer;

    private TextView practice_tv;
    private TextView search_tv;
    private TextView dict_tv;

    private static  List<Integer> sPics = new ArrayList<>();

    //创建ListView 存储数据
    private List<Article> mArticleList = new ArrayList<>();

    static{
        sPics.add(R.drawable.lb1);
        sPics.add(R.drawable.lb2);
        sPics.add(R.drawable.lb3);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
          //自动循环
         mHandler = new Handler();
         mHandler.post(mTasker);

        return view;
    }

    //初始化首页文章数据
    private void initArticle() {

        ArticleService articleService = new ArticleService();
        mArticleList = articleService.queryAllArticles(getContext());

    }



    private Runnable mTasker = new Runnable(){
        @Override
        public void run() {
            if (!mIsTouch) {
                //切换图片到下一个
                int currentItem = mLooperPager.getCurrentItem();
                mLooperPager.setCurrentItem(++currentItem,true);
            }

            mHandler.postDelayed(this,3000);
        }
    };

    private void initView(View view) {
        //找到viewPaper
        mLooperPager = view.findViewById(R.id.looper_paper);
        //设置适配器
        mLooperPagerAdapter =  new LooperPagerAdapter();
        //设置数据
        mLooperPagerAdapter.setData(sPics);

        mLooperPager.setAdapter(mLooperPagerAdapter);
        mLooperPager.setOnPageChangeListener(this);
         pointer = (LinearLayout)view.findViewById(R.id.points);
        //添加小圆点
        initPoint();
        //设置初始值
        mLooperPager.setCurrentItem(mLooperPagerAdapter.getDataSize() * 100,false);
    }

    private void initPoint() {
        for (int i = 0; i < sPics.size(); i++) {
            ImageView point = new ImageView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20,20);
            point.setBackground(getResources().getDrawable(R.drawable.shap_point_normal));
            layoutParams.leftMargin=10;
            layoutParams.bottomMargin=20;
            layoutParams.gravity= Gravity.BOTTOM;
            point.setLayoutParams(layoutParams);
            pointer.addView(point);

        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //viewpaper停下来的选中的位置
        int mposition ;
        if( mLooperPagerAdapter.getDataSize() != 0 ) {
             mposition = position % mLooperPagerAdapter.getDataSize();
        }else{
            mposition=0;
        }
        setSelectPointer(mposition);
    }

    private void setSelectPointer(int mposition) {
        for (int i = 0; i < pointer.getChildCount(); i++) {
            View point = pointer.getChildAt(i);
            if(i!=mposition){
                point.setBackgroundResource(R.drawable.shap_point_normal);
            }else{
                point.setBackgroundResource(R.drawable.shap_point_selected);
            }
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        practice_tv =  getActivity().findViewById(R.id.practiseId);
        practice_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PractiseActivity.class);
                startActivity(intent);
            }
        });

        search_tv =  getActivity().findViewById(R.id.searchId);
        search_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        dict_tv =  getActivity().findViewById(R.id.dictId);
        dict_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DictActivity.class);
                startActivity(intent);
            }
        });

        //处理文章数据
        //初始化数据
        initArticle();
        ActisAdapter adapter=new ActisAdapter(getActivity(),R.layout.actilist_item,mArticleList);

        // 将适配器上的数据传递给listView
        ListView listView=getActivity().findViewById(R.id.actiListViewId);
        listView.setAdapter(adapter);
        setListViewHeight(listView);
        //绑定文章点击事件
        // 为ListView注册一个监听器，当用户点击了ListView中的任何一个子项时，就会回调onItemClick()方法
        // 在这个方法中可以通过position参数判断出用户点击的是那一个子项
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article=mArticleList.get(position);
                Intent intent=new Intent(getActivity(), ArticleDetailActivity.class);
                //加入参数，传递给AnotherActivity
                intent.putExtra("artid",article.getId().toString());
                startActivity(intent);
            }
        });
    }


    /**
     * 点击事件
     */
    public void goPractise(View view){

    }

    public void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1))
                + 15;
        listView.setLayoutParams(params);
    }
}