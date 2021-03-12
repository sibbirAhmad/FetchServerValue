package soft.insafservice.fetchservervalue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText districtEDT;
    TextView resultTV;
    Button getBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        districtEDT = findViewById(R.id.district_name_edt);
        resultTV = findViewById(R.id.resultTV);
        getBTN = findViewById(R.id.get_btn);


        getBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String district = districtEDT.getText().toString();
                if(!district.isEmpty()){
                    getValue(district);
                }


            }
        });
    }

    public void getValue (String districtName){
        String url  = "http://api.aladhan.com/v1/timingsByCity?city="+districtName+"&country=bangladesh&method=8";
        ConnectionApi connectionApi = new ConnectionApi(url,this);
        //---------todo : Setting Listener for Result
        connectionApi.setConnectionListener(new ConnectionApi.ConnectionListener() {
            @Override
            public void onSuccess(String resultText) {
              //todo : Result Got
                resultTV.setText(resultText);

                String json = resultText; //todo : Analyze json As your expectation
            }

            @Override
            public void onFailure(String ErrorText) {
                Toast.makeText(MainActivity.this, ErrorText, Toast.LENGTH_SHORT).show();

            }
        });

    }
}
