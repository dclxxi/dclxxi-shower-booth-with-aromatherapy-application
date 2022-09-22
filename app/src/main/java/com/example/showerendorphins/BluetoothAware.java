package com.example.showerendorphins;

public interface BluetoothAware {
    void connect();
    void startScan(int index);
    void stopService();
    void send(String text);
    void setAroma(int aromaId);
    void receive(int index);
}
