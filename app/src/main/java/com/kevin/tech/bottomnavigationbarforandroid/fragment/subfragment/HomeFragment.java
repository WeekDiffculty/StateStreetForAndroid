package com.kevin.tech.bottomnavigationbarforandroid.fragment.subfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kevin.tech.bottomnavigationbarforandroid.Constants;
import com.kevin.tech.bottomnavigationbarforandroid.R;
import com.kevin.tech.bottomnavigationbarforandroid.fragment.subfragment.Hq.Model.Hqmodel;
import com.kevin.tech.bottomnavigationbarforandroid.fragment.subfragment.Hq.Model.Httprequest;
import com.kevin.tech.bottomnavigationbarforandroid.fragment.subfragment.Hq.Model.NSUserdefault;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Handler;

/**
 * Created by Kevin on 2016/11/28.
 * Blog:http://blog.csdn.net/student9128
 * Description: HomeFragment
 */

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener,Handler.Callback{

    public static final int  SUCCESS = 999;
    private static final int  FAIL = 888;
    private List<Hqmodel> listArray;
    private ListView listView;
    private TextView symbolName;
    private TextView time;
    private TextView price;
    private TextView price2;
    private Handler handler;
    private int count = 0;
    private Mydaper mydaper;

    public static HomeFragment newInstance(String s){
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS,s);
        homeFragment.setArguments(bundle);
        return homeFragment;
}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hq_list, container, false);


        handler = new Handler(this);

      final   List<String> list = getSymbolList();
        Log.i("", "onCreateView: "+list);
        count = list.size();

        //定时任务
        final Handler dalayHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadData(list);
                dalayHandler.postDelayed(this,15000);
            }
        };
        //启动计时器
                dalayHandler.postDelayed(runnable,15000);
        //停止计时器
//                handler.removeCallbacks(runnable);
        //加载数据的方法
        loadData(list);

        listView = (ListView) view.findViewById(R.id.listView_hq);
        mydaper = new Mydaper();
        listView.setAdapter(mydaper);//第一种绑定数据

        listView.setOnItemClickListener(this);
        listView.setMinimumHeight(100);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getContext(),"点击："+position+"行"+listArray.get(position),Toast.LENGTH_LONG).show();
        Snackbar.make(getView(),"点击："+position+"行",Snackbar.LENGTH_SHORT ).show();

    }

    /*
    *数据适配器
    *
     */
    class Mydaper extends BaseAdapter{

        @Override//定义listView的长度
        public int getCount() {
            if (listArray!=null){
                return listArray.size();
            }
            else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override//
        public View getView(int position, View convertView, ViewGroup parent) {
            View cell = null;
            Hqmodel hqmodel = listArray.get(position);
            if (convertView != null){
                cell = (View) convertView;
            }else {
                //把xml文件变成View对象 布局填充器对象
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                cell = layoutInflater.inflate(R.layout.item_hq_view,null);
            }
            symbolName = (TextView) cell.findViewById(R.id.symbol_name);
            symbolName.setText(hqmodel.getSymbol());
            time = (TextView) cell.findViewById(R.id.time_hq);
            time.setText(hqmodel.getTime());
            String curentPrice = hqmodel.getPrince();
            price = (TextView) cell.findViewById(R.id.price);
            price2 = (TextView) cell.findViewById(R.id.price2);
           int princeLast = Integer.valueOf(curentPrice.substring(curentPrice.length()-1)) ;
            Log.i("", "getView: DLFSJFkjdskfjsd;fksad"+princeLast);
            if (princeLast % 2 ==0){
                price.setTextColor(Color.parseColor("#FF3333"));
                price2.setTextColor(Color.parseColor("#FF3333"));
            }else {
                price.setTextColor(Color.parseColor("#006699"));
                price2.setTextColor(Color.parseColor("#006699"));
            }
            price.setText(curentPrice);
            price2.setText(curentPrice);
            return cell;
        }
        //得到数据，重新刷新界面
        public void  addData(Hqmodel hqmodel){

            listArray.add(hqmodel);
            notifyDataSetChanged();//此方法会重绘ListView
        }
        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }
            private  void loadData(final List<String> list){
                if (listArray !=null){listArray =null;}
                listArray = new ArrayList<Hqmodel>();//重新初始化数据
                final   Httprequest httprequest = new Httprequest();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String symbol = null;
                            for (int i = 0;i<count;i++) {
                                symbol = list.get(i);
                             Hqmodel hqmodel = httprequest.requestListHq(symbol);//请求

                                if (hqmodel != null) {

                                    Message msg = new Message();
                                    msg.what = SUCCESS;
                                    msg.obj = hqmodel;
                                    handler.sendMessage(msg);
                                } else {
                                    Message msg = new Message();
                                    msg.what = FAIL;
                                    handler.sendMessage(msg);
                                }
                            }
                        }
                    }).start();

}
    @Override
    public boolean handleMessage(Message msg) {
       switch (msg.what){
           case SUCCESS:
               Hqmodel hqmodel = (Hqmodel) msg.obj;
               updateUI(hqmodel);

               break;
           case FAIL:

               break;
           default:
               break;
       }

        return true;
    }

    public void  updateUI(Hqmodel hqmodel){

        Log.i("", "updateUI: "+listArray.toString());
           mydaper.addData(hqmodel);

    }

    //获取产品列表
    private List<String> getSymbolList(){

        Integer symbolcount = 0;
        symbolcount = (Integer) NSUserdefault.get(getContext(),"symolCount",symbolcount);
        List<String> symbollist = new ArrayList<String>();
        if (symbolcount == 0)
          {
        String[] list = {"EURUSD", "EURJPY", "EURGBP", "USDJPY", "USDCHF", "USDCAD", "AUDJPY", "AUDUSD", "GBPJPY", "GBPUSD", "NZDJPY", "NZDUSD", "XAUUSD", "XAGUSD", "EUROPE"};

        int count = list.length;
        for (Integer i = 0; i < count; i++) {
            symbollist.add(list[i]);
        }
        saveSymbolList(symbollist);
     }else {
            for (Integer k = 0;k< symbolcount;k++){
                String symbol = (String) NSUserdefault.get(getContext(),"LISTSYMBOL" + k, new String());
                symbollist.add(symbol);
            }
        }
        return symbollist;
    }

    //存储产品列表
    private void  saveSymbolList(List<String> list){
        Integer count = list.size();
        NSUserdefault.put(getContext(),"symolCount",count);
        for (Integer i = 0;i< count;i++) {
            NSUserdefault.put(getContext(), "LISTSYMBOL" + i, list.get(i));
        }
    }


}
