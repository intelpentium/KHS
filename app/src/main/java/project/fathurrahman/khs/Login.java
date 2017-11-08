package project.fathurrahman.khs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import project.fathurrahman.khs.Guru.LoginGuru;
import project.fathurrahman.khs.Ortu.LoginOrtu;
import project.fathurrahman.khs.R;

/**
 * Created by Fathurrahman on 02/12/2016.
 */

public class Login extends AppCompatActivity {

    Button parents,teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        parents = (Button)findViewById(R.id.parents);
        teacher = (Button)findViewById(R.id.teacher);

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(getApplication(), LoginGuru.class);
                startActivity(it);

            }
        });

        parents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent it = new Intent(getApplication(), LoginOrtu.class);
                startActivity(it);
            }
        });
    }
}
