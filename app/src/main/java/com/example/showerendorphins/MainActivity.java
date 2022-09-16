package com.example.showerendorphins;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.showerendorphins.databinding.ActivityMainBinding;
import com.example.showerendorphins.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class MainActivity extends AppCompatActivity implements BluetoothAware {
    boolean flag = false;
    int fragIndex = 0;
    private BluetoothSPP bt;
    private ActivityMainBinding binding;
    String mood = "";
    String aroma = "";

    BluetoothAdapter btAdapter;
    private final static int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        // Get permission
        String[] permission_list = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        ActivityCompat.requestPermissions(MainActivity.this, permission_list, 1);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*SIDE menu*/
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        final NavigationView navigationView = findViewById(R.id.navigationView);


        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start에 지정된 Drawer 열기
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setItemIconTintList(null);
        NavigationViewHelper.enableNavigation(MainActivity.this, navigationView);



        /*main tab menu*/

        /*BOTTOM TAB*/
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        /*Blooth*/
        bt = new BluetoothSPP(this); //Initializing
        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext(), "Bluetooth is not available",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        connect();

    }

    private void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void replaceFragment(int index) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (index) {
            case 1: // SERVICE
                fragmentTransaction.replace(R.id.frame_layout, new ServiceFragment()).commitAllowingStateLoss();
                break;
            case 2: // HEIGHT
                fragmentTransaction
                        .replace(R.id.frame_layout, new HeightFragment()).commitAllowingStateLoss();
                break;
            case 3: // SHOWER_HEAD
                fragmentTransaction
                        .replace(R.id.frame_layout, new ShowerHeadFragment()).commitAllowingStateLoss();
                break;
            case 4: // MOOD
                fragmentTransaction
                        .replace(R.id.frame_layout, new MoodFragment()).commitAllowingStateLoss();
                break;
            case 5: // RECOMMENDATION
                Bundle bundle = new Bundle();
                bundle.putString("mood",mood); // mood값 전달
                RecommendationFragment recommendationFragment = new RecommendationFragment();
                recommendationFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_layout, recommendationFragment).commitAllowingStateLoss();

//                fragmentTransaction
//                        .replace(R.id.frame_layout, new RecommendationFragment()).commitAllowingStateLoss();
                break;
            case 6: // SELECTION
                fragmentTransaction
                        .replace(R.id.frame_layout, new SelectionFragment()).commitAllowingStateLoss();
                break;
            case 7: // USER_TEMP
                fragmentTransaction
                        .replace(R.id.frame_layout, new UserTempFragment()).commitAllowingStateLoss();
                break;
            case 8: // WATER_TEMP
                fragmentTransaction
                        .replace(R.id.frame_layout, new WaterTempFragment()).commitAllowingStateLoss();
                break;
            case 9: // WATER
                fragmentTransaction
                        .replace(R.id.frame_layout, new WaterFragment()).commitAllowingStateLoss();
                break;
            case 10: // EVALUATION
                fragmentTransaction
                        .replace(R.id.frame_layout, new EvaluationFragment()).commitAllowingStateLoss();
                break;
            case 11: // HOME
                fragmentTransaction
                        .replace(R.id.frame_layout, new HomeFragment()).commitAllowingStateLoss();
                break;
        }
    }

    @Override
    public void connect() {
        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {
                Toast.makeText(getApplicationContext(), "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
                flag = true;
                if (flag == true) {
                    replaceFragment(fragIndex);
                }
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext(), "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onDestroy() {
        super.onDestroy();
//        bt.stopService(); //블루투스 중지
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
            }
        }
    }

    @Override
    public void startScan(int index) {
        fragIndex = index;
        if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
            bt.disconnect();
        } else {
            Intent intent = new Intent(getApplicationContext(), DeviceList.class);
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
        }
    }

    @Override
    public void stopService() {
        bt.stopService(); //블루투스 중지
    }

    @Override
    public void send(String text) { //데이터 전송
        bt.send(text, true);
    }

    @Override
    public void setAroma(String aroma) {
        this.aroma = aroma;
    }

    @Override
    public void receive(int index) {
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
//                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                if (index == 2) {
                    //키 저장
                    int height = Integer.parseInt(message);
                } else if (index == 4) {
                    int i = Integer.parseInt(message);
                    //사용자 기분 저장
                    if ( i== 1) {   //happy
                        mood = "HAPPY";
                    } else if (i == 2) {//angry
                        mood = "ANGRY";
                    } else if (i == 3) {    //sad
                        mood = "SAD";
                    }
                } else if (index == 7) {
                    System.out.println("체온 저장");
                    int temp = Integer.parseInt(message);
                }
                replaceFragment(index + 1);

            }

        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                bt.connect(data);
            }
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }
}