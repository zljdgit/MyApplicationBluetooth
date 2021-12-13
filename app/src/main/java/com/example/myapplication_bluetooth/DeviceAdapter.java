package com.example.myapplication_bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public abstract class DeviceAdapter extends BaseAdapter {
    private Context mContext;
    private List<BluetoothDevice> mDate;

    public DeviceAdapter(List<BluetoothDevice> Date, Context context){
        mDate = Date;
        mContext = context;

    }
    @Override
    public int getCount() {
        return mDate.size();
    }

    @Override
    public Object getItem(int position) {
        return mDate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            viewHolder.textView1 = (TextView)convertView.findViewById(R.id.textView1);
            viewHolder.textView2 = (TextView)convertView.findViewById(R.id.textView2);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //获取蓝牙设备
        BluetoothDevice bluetoothDevice = (BluetoothDevice) getItem(position);
        viewHolder.textView1.setText("Name="+bluetoothDevice.getName());
        viewHolder.textView2.setText("Address"+bluetoothDevice.getAddress());
        return convertView;
    }

    public class ViewHolder{
        public TextView textView1;
        public  TextView textView2;

    }
    public void refresh(List<BluetoothDevice> data){
        mDate = data;
        notifyDataSetChanged();
    }
}
