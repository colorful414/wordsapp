package cn.itfxq.wordsapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import cn.itfxq.wordsapp.R;
import cn.itfxq.wordsapp.activity.LoginActivity;
import cn.itfxq.wordsapp.activity.RegActivity;
import cn.itfxq.wordsapp.util.DBUtils;


public class MyFragment extends Fragment {

    View myview ;
    TextView  logout_tv;
    TextView  nametv;
    TextView  emailtv;
    TextView  teltv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myview =  inflater.inflate(R.layout.fragment_my, container, false);
        logout_tv = myview.findViewById(R.id.logout);

        initDatas(myview);
        return myview;
    }

    private void initDatas(View myview) {
        nametv = myview.findViewById(R.id.my_name);
        emailtv = myview.findViewById(R.id.my_email);
        teltv = myview.findViewById(R.id.my_tel);
        //读取个人信息
        Map<String, String> userMap = DBUtils.readInfo();
        nametv.setText(userMap.get("username"));
        emailtv.setText(userMap.get("email"));
        teltv.setText(userMap.get("tel"));
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        logout_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),LoginActivity.class);
                //启动
                startActivity(intent);
            }
        });
    }


}