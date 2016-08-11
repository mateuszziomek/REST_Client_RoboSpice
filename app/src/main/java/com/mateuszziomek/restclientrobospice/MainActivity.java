package com.mateuszziomek.restclientrobospice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class MainActivity extends AppCompatActivity {

    protected SpiceManager spiceManager = new SpiceManager(JacksonSpringAndroidSpiceService.class);

    private TextView resultText;
    private Button refreshButton;

    private void initUIComponents() {
        resultText = (TextView) findViewById(R.id.text);
        refreshButton = (Button) findViewById(R.id.button);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRequest();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUIComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    private void performRequest() {
        // The execute method invokes loadDataFromNetwork method declared in GeoRequest.class
        GeoRequest geoRequest = new GeoRequest();
        spiceManager.execute(geoRequest, new CustomRequestListener());
    }

    private final class CustomRequestListener implements RequestListener<String> {
        // Listeners to notify when the request is finished.
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(MainActivity.this, "Error: " + spiceException.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(String result) {
            resultText.setText(getString(R.string.result_text, result));
        }
    }


}
