package com.example.showerendorphins;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.showerendorphins.enums.FragmentIndex;
import com.example.showerendorphins.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class MainActivity extends AppCompatActivity implements BluetoothAware {
    boolean flag = false;
    FragmentIndex fragIndex;
    private BluetoothSPP bt;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    String email = "";
    String mood = "";
    int height = 0;
    int aromaId = 0;
    int userTemp = 0;
    int waterTemp = 0;

    BluetoothAdapter btAdapter;
    private final static int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        email = user.getEmail();

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

    public void replaceFragment(FragmentIndex index) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();

        switch (index) {
            case SERVICE: // SERVICE
                fragmentTransaction.replace(R.id.frame_layout, new ServiceFragment()).commitAllowingStateLoss();
                break;
            case HEIGHT: // HEIGHT
                fragmentTransaction.replace(R.id.frame_container, new HeightFragment()).commitAllowingStateLoss();
                break;
            case SHOWER_HEAD: // SHOWER_HEAD
                fragmentTransaction.replace(R.id.frame_container, new ShowerHeadFragment()).commitAllowingStateLoss();
                break;
            case MOOD: // MOOD
                fragmentTransaction.replace(R.id.frame_container, new MoodFragment()).commitAllowingStateLoss();
                break;
            case RECOMMENDATION: // RECOMMENDATION

                email = "aa@test.com";
                mood = "ANGRY";

                bundle.putString("userId", email);
                bundle.putString("feeling", mood); // mood값 전달
                RecommendationFragment recommendationFragment = new RecommendationFragment();
                recommendationFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_container, recommendationFragment).commitAllowingStateLoss();
                break;
            case SELECTION: // SELECTION
                email = "aa@test.com";

                bundle.putString("userId", email);
                SelectionFragment selectionFragment = new SelectionFragment();
                selectionFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_container, selectionFragment).commitAllowingStateLoss();
                break;
            case USER_TEMP: // USER_TEMP
                fragmentTransaction.replace(R.id.frame_container, new UserTempFragment()).commitAllowingStateLoss();
                break;
            case WATER_TEMP: // WATER_TEMP
                fragmentTransaction.replace(R.id.frame_container, new WaterTempFragment()).commitAllowingStateLoss();
                break;
            case WATER: // WATER
                fragmentTransaction.replace(R.id.frame_container, new WaterFragment()).commitAllowingStateLoss();
                break;
            case EVALUATION: // EVALUATION
                bundle.putString("email", email);
                bundle.putString("height", String.valueOf(height));
                bundle.putString("feeling", mood);
                bundle.putString("bodyTemperature", String.valueOf(userTemp));
                bundle.putString("waterTemperature", String.valueOf(waterTemp));
                bundle.putString("aroma", String.valueOf(aromaId)); //번호 전달
                EvaluationFragment evaluationFragment = new EvaluationFragment();
                evaluationFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_layout2, evaluationFragment).commitAllowingStateLoss();
                break;
            case HOME: // HOME
                fragmentTransaction.replace(R.id.frame_layout, new HomeFragment()).commitAllowingStateLoss();
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
    public void startScan(FragmentIndex index) {
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
    public void setAroma(int aromaId) {
        this.aromaId = aromaId;
    }

    @Override
    public void receive(FragmentIndex index) {
        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신
            public void onDataReceived(byte[] data, String message) {
//                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

                int msg = Integer.parseInt(message);

                if (!index.equals(FragmentIndex.MOOD)) {
                    if (msg != 1) {
                        if (index.equals(FragmentIndex.HEIGHT)) {
                            height = msg; // 키 저장
                        } else if (index.equals(FragmentIndex.USER_TEMP)) {
                            userTemp = msg; // 체온 측정
                        } else if (index.equals(FragmentIndex.WATER_TEMP)) {
                            waterTemp = msg; // 체온 측정
                        }
                    }
                } else {
                    switch (msg) {  // 사용자 기분 저장
                        case 1:
                            mood = "HAPPY";
                            break;
                        case 2:
                            mood = "ANGRY";
                            break;
                        case 3:
                            mood = "SAD";
                            break;
                    }
                }
                replaceFragment(index);
            }
//                if (!message.equals("1")) {
//                    if (index.equals(FragmentIndex.HEIGHT)) {
//                        height = Integer.parseInt(message); // 키 저장
//                    } else if (index.equals(FragmentIndex.MOOD)) {
//                        int i = Integer.parseInt(message); // 사용자 기분 저장
//                        if (i == 1) {           // happy
//                            mood = "HAPPY";
//                        } else if (i == 2) {    // angry
//                            mood = "ANGRY";
//                        } else if (i == 3) {    // sad
//                            mood = "SAD";
//                        }
//                    } else if (index.equals(FragmentIndex.USER_TEMP)) {
//                        temp = Integer.parseInt(message);
//                    }
//                }
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
                Toast.makeText(getApplicationContext(), "Bluetooth was not enabled.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}