package com.example.showerendorphins;

public interface BluetoothAware {
    void connect();
    void startScan(int index);
    void stopService();
    void send(String text);
    void receive();
}
