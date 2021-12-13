package com.example.myapplication_bluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = null;
    private DeviceAdapter mAdapter;
    private List mList;
    private BluetoothAdapter mBluetoothAdapter;
    //定义广播接收者，用于处理扫描蓝牙设备后的结果
    private BroadcastReceiver mReceiverBluetoothStatus = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context,Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                // 开始搜索        ——接收广播
                Log.d(TAG,"开始搜索");
                mList.clear();
                mAdapter.refresh(mList);

            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                // 查找到设备完成   —— 接收广播
                Log.d(TAG,"查找到设备完成");

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 搜索到设备       —— 接收广播
                Log.d(TAG,"搜索到设备");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mList.add(device);
                mAdapter.refresh(mList);


            } else if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {
                // 当自己设备设置蓝牙可见时或者不可见时 —— 接收广播
                int scanMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,0);
                // 可见时
                if (scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    Log.d(TAG,"设备可见监听");
                } else {
                    Log.d(TAG,"设备不可见监听");
                }

            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                // 绑定状态改变回调
                BluetoothDevice remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (remoteDevice == null) {
                    Log.d(TAG,"没有绑定设备");
                    return;
                }

                int status = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE,0);
                if (status == BluetoothDevice.BOND_BONDED) {
                    Log.d(TAG,"绑定设备完成: " + remoteDevice.getName());
                } else if (status == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG,"绑定设备中: " + remoteDevice.getName());
                } else if (status == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG,"取消绑定: ");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}