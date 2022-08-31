package com.example.showerendorphins;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.showerendorphins.databinding.ActivityMainBinding;
import com.example.showerendorphins.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    FragmentManager manager;
    FragmentTransaction transaction;

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

//        // Enable bluetooth
//        btAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (!btAdapter.isEnabled()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//        }
    }

    private void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void replaceFragment(int index) {

        switch (index) {
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new ServiceFragment()).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, new MeasurementFragment()).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, new ShowerFragment()).commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, new RecommendationFragment()).commit();
                break;
            case 5:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, new SelectionFragment()).commit();
                break;
            case 6:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, new WaterFragment()).commit();
                break;
            case 7:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout2, new EvaluationFragment()).commit();
                break;
            case 8:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, new HomeFragment()).commit();
                break;
        }
    }
}