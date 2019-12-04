package com.example.printdemoapp50;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<Map<String, String>> mDataMapList;
    private SimpleAdapter mAdapter;
    private static final String DEVICE_NAME_KEY    = "DEVICE_NAME_KEY";
    private static final String IDENTIFIER_KEY     = "IDENTIFIER_KEY";
    private StarIOPort mStarIoPort;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0x00);
        }

        ListView discoveredListView = findViewById(R.id.DiscoveredListView);

        mDataMapList = new ArrayList<>();
        mAdapter = new SimpleAdapter(this,mDataMapList,R.layout.list_discovered_row
                ,new String[] {DEVICE_NAME_KEY, IDENTIFIER_KEY},
                new int[] { R.id.DeviceNameTextView,R.id.IdentifierTextView});
        discoveredListView.setAdapter(mAdapter);  // this is your simple adapter

        discoveredListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDataMapList.clear();
                mAdapter.notifyDataSetChanged();

                TextView identifierTextView    = view.findViewById(R.id.IdentifierTextView);
                String   identifier            = identifierTextView.getText().toString();

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra(Main2Activity.IDENTIFIER_BUNDLE_KEY,  identifier);

                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            List<PortInfo> portInfos = StarIOPort.searchPrinter("BT:",this);
            for (int i =0; i< portInfos.size(); i++ ){
                Map<String, String> map = new HashMap<>();
                map.put(DEVICE_NAME_KEY, portInfos.get(i).getModelName());
                map.put(IDENTIFIER_KEY, portInfos.get(i).getMacAddress());

                mDataMapList.add(map);
            }
            mAdapter.notifyDataSetChanged();
        }catch (StarIOPortException e) {
            e.printStackTrace();
        }

    }
}
