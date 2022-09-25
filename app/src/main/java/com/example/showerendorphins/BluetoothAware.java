package com.example.showerendorphins;

import com.example.showerendorphins.enums.FragmentIndex;

public interface BluetoothAware {
    void connect();
    void startScan(FragmentIndex index);
    void stopService();
    void send(String text);
    void setAroma(int aromaId);
    void receive(FragmentIndex index);
}
