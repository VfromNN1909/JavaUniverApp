package ru.vladimirvlasoff.univerapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity{

    private WebView webViewUniversity;
    private Button buttonGetWebPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webViewUniversity = findViewById(R.id.webViewUniversity);
        webViewUniversity.getSettings().setJavaScriptEnabled(true);
        webViewUniversity.setWebViewClient(new MyWebClient());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    public void buildAlertDialog(){
        // получаем layout,который применим для диалогового окна
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialogView = layoutInflater.inflate(R.layout.alert_dialog_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(dialogView);

        final EditText userInput = (EditText) dialogView.findViewById(R.id.input_text);

        builder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        webViewUniversity.loadUrl(userInput.getText().toString());
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
            AlertDialog alertDialog = builder.create();

            alertDialog.show();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
        // через свич определяем какая иконка нажата
        String mininURL = "https://ya.mininuniver.ru/shedule";
        switch(menu.getItemId()){
            case R.id.getScheduleMenu:
                webViewUniversity.loadUrl(mininURL);
                break;
            case R.id.searchPageMenu:
                buildAlertDialog();
                break;

        }
        return super.onOptionsItemSelected(menu);
    }


    private class MyWebClient extends WebViewClient{
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }
}
