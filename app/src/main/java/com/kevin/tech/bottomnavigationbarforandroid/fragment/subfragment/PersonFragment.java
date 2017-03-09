package com.kevin.tech.bottomnavigationbarforandroid.fragment.subfragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.kevin.tech.bottomnavigationbarforandroid.Constants;
import com.kevin.tech.bottomnavigationbarforandroid.R;
import com.kevin.tech.bottomnavigationbarforandroid.fragment.subfragment.setUp.About;
import com.kevin.tech.bottomnavigationbarforandroid.fragment.subfragment.setUp.KefuActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin on 2016/11/28.
 * Blog:http://blog.csdn.net/student9128
 * Description:
 */

public class PersonFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView listView1;
    private ListView listView2;

    private List<Map<String,Object>> list;

    public PersonFragment() {
    }


    public static PersonFragment newInstance(String s) {
        PersonFragment homeFragment = new PersonFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_content, container, false);
        listView1 = (ListView) view.findViewById(R.id.setuplistview1);
        listView2 = (ListView) view.findViewById(R.id.setuplistview2);
        //list1 绑定数据源
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("icon",R.drawable.account);
        map.put("name", "账户");
        data.add(map);
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),//上下文
                data,// 绑定数据
                R.layout.setup_items,//listView 子条目布局id
                new String[]{"icon","name"},//data数据中map的key
                new int []{R.id.setupicon,R.id.setupitemname
                }//resouse中的id
        );
        listView1.setAdapter(simpleAdapter);
        listView1.setOnItemClickListener(this);

        //list2 绑定数据源

        list = new ArrayList<Map<String, Object>>();
        String name = null;
        Object iconObj = null;
        for (int i = 0;i< 3;i++){

            Map<String,Object> mapin = new HashMap<String, Object>();
            if (i==0){
                name = "图表";
                iconObj = R.drawable.chart;
            }else if (i==1){
                name = "联系客服";
                iconObj = R.drawable.custum;
            }else if (i==2){
                name = "关于";
                iconObj = R.drawable.about;
            }
            mapin.put("name",name);
            mapin.put("icon",iconObj);
            list.add(mapin);
        }


        listView2.setAdapter(new Mydaper());//第一种绑定数据
        listView2.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    if (parent.getId()== R.id.setuplistview1){//账户
    Toast.makeText(getContext(),"1组"+position+"行",Toast.LENGTH_LONG).show();
    }else if (parent.getId() == R.id.setuplistview2){//第二组
        Toast.makeText(getContext(),"2组"+position+"行",Toast.LENGTH_LONG).show();
        switch (position){
            case 0:

                break;
            case 1:
                //意图
                Intent intent = new Intent(getContext(), KefuActivity.class);
               // intent.setClassName(getPackageName(),".fragment.subfragment.setUp.KefuActivity");//显示意图，不指定动作，不指定数据，直接启动组件
                startActivity(intent);
                //intent.setAction();
                //intent.setData(); //隐士意图，

                break;
            case 2:
                Intent intents = new Intent(getContext(), About.class);
                startActivity(intents);
                break;
            default:
                break;
        }
    }
    }

    class Mydaper extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View cell = null;
            ImageView imageView = null;
            TextView textView = null;
            Map<String,Object> map = list.get(position);
            if (convertView != null)
            {
                cell = (View) convertView;

            }else {
                //把xml文件变成View对象 布局填充器对象
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                cell = layoutInflater.inflate(R.layout.setup_items,null);
            }
            //从中取出对应的imageview 和 textView
            imageView = (ImageView) cell.findViewById(R.id.setupicon);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), (Integer) map.get("icon"));
            bitmap = getRoundedCornerBitmap(bitmap,10);
            imageView.setImageBitmap(bitmap);
            textView = (TextView) cell.findViewById(R.id.setupitemname);
            String string = (String) map.get("name");
            textView.setText(string);
            return cell;
        }
    }
    //获得圆角图片的方法
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
