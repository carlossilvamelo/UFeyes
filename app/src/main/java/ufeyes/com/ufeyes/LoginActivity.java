package ufeyes.com.ufeyes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ufeyes.com.ufeyes.serviceLayer.OAuthTokenRequest;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "ufeyes-id";
    private static final String CLIENT_SECRET = "segredo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
    }

    public void logar(View v) {
        Intent i = new Intent(this, MainActivity.class);
        OAuthTokenRequest.getInstance().getTokenCredential(this,"http://apitestes.info.ufrn.br/authz-server",CLIENT_ID, CLIENT_SECRET, i);
    }

    public void obterDados(View v){
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }

    //métodos omitidos

    public void sair(View view) {
        OAuthTokenRequest.getInstance().logout(this, "http://apitestes.info.ufrn.br/sso-server/logout");
    }
}

