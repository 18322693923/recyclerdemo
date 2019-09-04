package com.test.recyclerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.recyclerdemo.bean.Province;
import com.test.recyclerdemo.inter.OnItemViewClickListener;
import com.test.recyclerdemo.utis.GsonUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemViewClickListener {

    RecyclerView recyclerView;
    FloatItemDecoration floatItemDecoration;

    private List<Object> objects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //模拟数据
        List<Province> item = GsonUtils.jsonToList(TestData.DATA, Province.class);
        
        ExpandableAdapterView expandableListAdapter = new ExpandableAdapterView(item);
        recyclerView.setAdapter(expandableListAdapter);
        floatItemDecoration = new FloatItemDecoration(expandableListAdapter.getObjects(), this);
        recyclerView.addItemDecoration(floatItemDecoration);
        expandableListAdapter.setOnItemViewClickListener(this);
        expandableListAdapter.setOnUIChangeListener(floatItemDecoration);
    }


    @Override
    public void setOnViewClickListener(View view, int position, Province.City city) {
        Toast.makeText(this, "点击了第" + position + "条,当前城市：" + city.getCityName(), Toast.LENGTH_SHORT).show();
    }
}
