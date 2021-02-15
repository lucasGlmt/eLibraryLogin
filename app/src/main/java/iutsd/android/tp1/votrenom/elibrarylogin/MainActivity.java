package iutsd.android.tp1.votrenom.elibrarylogin;

import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int attempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        EditText textUsername = (EditText) findViewById(R.id.editTextUsername);
        EditText textPassword = (EditText) findViewById(R.id.editTextPassword);
        Switch switchAccept = (Switch) findViewById(R.id.switchAccept);
        RadioGroup radGroup = (RadioGroup) findViewById(R.id.radGroupSex);
        TextView textAttempts = (TextView) findViewById(R.id.textAttempts);
        Resources res = getResources();

        textAttempts.setText(String.format(res.getString(R.string.attempt_login), String.valueOf(attempts)));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                textAttempts.setText(String.format(res.getString(R.string.attempt_login), String.valueOf(attempts)));

                // If EditText are empty
                if(textUsername.getText().toString().equals("") || textPassword.getText().toString().equals("")) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Erreur").setMessage("Vous devez entrer un mot de passe et un nom d'utilisateur.").show();
                    return;
                }

                // If Swtich is not checked
                if(switchAccept.isChecked() == false) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Erreur").setMessage("Vous devez accepter les risques.").show();
                    return;
                }

                // If gender is not selected
                if(radGroup.getCheckedRadioButtonId() ==-1) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Erreur").setMessage("Merci de sélectionner votre genre.").show();
                    return;
                }

                if(attempts >= 3) {
                    new AlertDialog.Builder(MainActivity.this).setTitle("Erreur").setMessage("Vous avez épuisé tous vos essais.").show();

                    return;
                }

                // On essaie de se connecter
                String[] logins = getResources().getStringArray(R.array.logins);
                String[] passwords = getResources().getStringArray(R.array.passwords);

                for(int i = 0; i < logins.length; i++) {
                    if(textUsername.getText().toString().equals(logins[i])) {
                        if (passwords[i].equals(textPassword.getText().toString())) {
                            new AlertDialog.Builder(MainActivity.this).setTitle(R.string.welcome
                            ).show();
                            return;
                        }
                        new AlertDialog.Builder(MainActivity.this).setTitle(R.string.error).setMessage(R.string.bad_password).show();
                        attempts++;
                        return;
                    }
                }

                // Erreur de connexion
                attempts++;
                new AlertDialog.Builder(MainActivity.this).setTitle(R.string.error).setMessage(R.string.bad_username).show();
                textAttempts.setText(String.format(res.getString(R.string.attempt_login), String.valueOf(attempts)));

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("connectionAttempts", attempts);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        attempts = savedInstanceState.getInt("connectionAttempts");
        TextView textAttempts = (TextView) findViewById(R.id.textAttempts);
        textAttempts.setText(String.format(getResources().getString(R.string.attempt_login), String.valueOf(attempts)));
    }
}