package e.hp.firsttry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HOME1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
    }
    public void Lonavala(View view) {
        Intent intent1 = new Intent(this, LonavalaActivity.class);
        startActivity(intent1);
    }
}
