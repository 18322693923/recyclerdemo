package com.test.recyclerdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.recyclerdemo.bean.ChildItem;
import com.test.recyclerdemo.bean.ParentItem;
import com.test.recyclerdemo.bean.Province;
import com.test.recyclerdemo.inter.OnItemViewClickListener;
import com.test.recyclerdemo.inter.OnUIChangeListener;

import java.util.ArrayList;
import java.util.List;

public class ExpandableAdapterView extends RecyclerView.Adapter {
    private final static int VIEW_PARENT = R.layout.item_parent;//父级布局
    private final static int VIEW_CHILD = R.layout.item_child;//子级布局

    //定义一个Object类型的集合来存放混合了省和市的数据实体
    private List<Object> objects = new ArrayList<>();
    //省列表数据
    private List<Province> provinces;
    private OnItemViewClickListener onItemViewClickListener;
    private OnUIChangeListener onUIChangeListener;

    public ExpandableAdapterView(List<Province> item) {
        this.provinces = item;
    }

    public void setOnUIChangeListener(OnUIChangeListener onUIChangeListener) {
        this.onUIChangeListener = onUIChangeListener;
    }

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == VIEW_PARENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(VIEW_PARENT, parent, false);
            holder = new ParentHolder(view);
        } else if (viewType == VIEW_CHILD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(VIEW_CHILD, parent, false);
            holder = new ChildHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ParentHolder) {
            ParentHolder parentHolder = (ParentHolder) holder;
            Province province = (Province) objects.get(position);
            parentHolder.textView.setText(province.getProvinceName());
            //省item点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //更新展开折叠状态，如果当前是折叠，则变更为展开，如果当前是展开，则变成折叠
                    ((Province) objects.get(position)).setExpand(!((Province) objects.get(position)).isExpand());
                    //刷新列表UI
                    notifyDataSetChanged();
                }
            });
        } else if (holder instanceof ChildHolder) {
            ChildHolder parentHolder = (ChildHolder) holder;
            final Province.City city = (Province.City) objects.get(position);
            parentHolder.textView.setText(city.getCityName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemViewClickListener != null) {
                        onItemViewClickListener.setOnViewClickListener(holder.itemView, position, city);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //每次刷新获取数据总数时先将原数据清空
        objects.clear();
        //获取itemcount思路：
        //列表要展示item总数=省总数+展开省中市的总数
        //比如有4个省，每个省下面列了10个市，
        //如果4个省全部折叠，那么要展示item总数=4，即只有4个省；
        //如果4个省全部展开，那么要展示的item总数= 4 + 4 * 10 = 44，即省和市个数总和。

        //首先给定基数size = 省的数目
        int size = provinces.size();
        //遍历省列表
        for (int i = 0; i < provinces.size(); i++) {
            //将每一个省的实体放进object集合里
            objects.add(provinces.get(i));
            //判断省展开状态，如果展开了，则省下面的市也要在界面展示出来
            if (provinces.get(i).isExpand()) {
                //将展开的省下面的市的总数赋给基数size
                size += provinces.get(i).getCities().size();
                //将该省下面所有市放进object集合里
                objects.addAll(provinces.get(i).getCities());
                //遍历所有市，将省名字赋给市，便于后面处理列表时知道当前市属于哪个省
                for (int j = 0; j < provinces.get(i).getCities().size(); j++) {
                    provinces.get(i).getCities().get(j).setProvinceName(provinces.get(i).getProvinceName());
                }
            }
        }
        if (onUIChangeListener != null) {
            onUIChangeListener.getItems(objects);
        }
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (objects.get(position) instanceof ParentItem) {
            //object中的实体类如果实现了父级实体类，表示该数据是省份的实体
            return VIEW_PARENT;
        } else if (objects.get(position) instanceof ChildItem) {
            //object中的实体类如果实现了子级实体类，表示该数据是市的实体
            return VIEW_CHILD;
        }
        return 0;
    }


    /**
     * 父级布局控件容器类
     */
    class ParentHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ParentHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_content);
        }
    }

    /**
     * 子级布局控件容器类
     */
    class ChildHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ChildHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_content);
        }
    }


    /**
     * 获取显示数据
     * @return
     */
    public List<Object> getObjects() {
        return objects;
    }
}
