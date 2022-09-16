package com.example.showerendorphins;

public interface BluetoothAware {
    void connect();
    void startScan(int index);
    void stopService();
    void send(String text);
    void setAroma(String aroma);
    void receive(int index);
}
