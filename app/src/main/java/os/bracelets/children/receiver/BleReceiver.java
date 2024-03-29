package os.bracelets.children.receiver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.huichenghe.bleControl.Ble.BluetoothLeService;
import com.huichenghe.bleControl.Ble.DeviceConfig;
import com.huichenghe.bleControl.Ble.LocalDeviceEntity;

import org.greenrobot.eventbus.EventBus;

import aio.health2world.utils.Logger;
import aio.health2world.utils.ToastUtil;
import os.bracelets.children.AppConfig;
import os.bracelets.children.MyApplication;
import os.bracelets.children.common.MsgEvent;

/**
 * Created by lishiyou on 2019/3/2.
 */

public class BleReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.i("lsy",intent.getAction());
        switch (intent.getAction()) {
            //设备已连接的广播
            case DeviceConfig.DEVICE_CONNECTE_AND_NOTIFY_SUCESSFUL:
                ToastUtil.showShort("设备连接成功");
                if(BluetoothLeService.getInstance()!=null){
                    //获取当前已连接的设备currentDevice
                    LocalDeviceEntity device = BluetoothLeService.getInstance().getCurrentDevice();
                    MyApplication.getInstance().setBleConnect(true);
                    MyApplication.getInstance().setDeviceEntity(device);
                    //连接成功后，对设备进行一系列检测请求，如电池电量等
                    EventBus.getDefault().post(new MsgEvent<LocalDeviceEntity>(AppConfig.MSG_DEVICE_CONNECT));
                }
                break;
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                MyApplication.getInstance().setBleConnect(false);
                MyApplication.getInstance().setDeviceEntity(null);
                MyApplication.getInstance().clearEntityList();
                break;
            case DeviceConfig.DEVICE_CONNECTING_AUTO:
//                ToastUtil.showShort("开始连接设备");
                break;
            case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                ToastUtil.showShort("设备失去连接");
                MyApplication.getInstance().setBleConnect(false);
                MyApplication.getInstance().setDeviceEntity(null);
                EventBus.getDefault().post(new MsgEvent<LocalDeviceEntity>(AppConfig.MSG_DEVICE_DISCONNECT));
                break;
        }
    }
}
