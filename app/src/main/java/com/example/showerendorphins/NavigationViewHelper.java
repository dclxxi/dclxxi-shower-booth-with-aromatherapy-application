package com.example.showerendorphins;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;

public class NavigationViewHelper {
    public static void enableNavigation(final Context context, NavigationView view) {
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    Toast.makeText(context, "logout", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.all_aroma_info) {
                    Intent intent = new Intent(context, AllAromaInfoList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                } else if (item.getItemId() == R.id.all_user_chart) {
                    Intent intent = new Intent(context, allUserBarChart.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                } else if (item.getItemId() == R.id.service_feedback) {
                    EmailUtils.sendEmailToAdmin(context, "개발자에게 메일보내기", new String[]{"ty009538@gmail.com"});
                }
                return true;
            }
        });

    }

}
class EmailUtils {
    public static void sendEmailToAdmin(Context context, String title, String[] receivers){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_SUBJECT, title);
        email.putExtra(Intent.EXTRA_EMAIL, receivers);
        email.putExtra(Intent.EXTRA_TEXT,"문의 내용을 입력해주세요 : ");
        email.setType("message/rfc822");
        context.startActivity(email);
    }
}