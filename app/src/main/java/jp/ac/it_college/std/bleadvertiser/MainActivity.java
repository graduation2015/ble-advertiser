package jp.ac.it_college.std.bleadvertiser;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener {

    private Advertise mAdvertise;

    private SwitchCompat mAdvertiseSwitch;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initSettings();
    }

    private void initSettings() {
        enableBluetooth();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mAdvertiseSwitch = (SwitchCompat)menu.findItem(R.id.menu_advertise_switch).getActionView();
        mAdvertiseSwitch.setOnCheckedChangeListener(this);
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            startAdvertise();
        } else {
            stopAdvertise();
        }
    }

    public RadioGroup getRadioGroup() {
        if (mRadioGroup == null) {
            mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        }

        return mRadioGroup;
    }

    private String getCheckedButtonText() {
        TextView textView = (TextView) findViewById(getRadioGroup().getCheckedRadioButtonId());
        return textView.getText().toString();
    }

    private void startAdvertise() {
        if (mAdvertise == null && getBluetoothAdapter() != null && getBluetoothAdapter().isEnabled()) {
            mAdvertise = new Advertise(getCheckedButtonText());
            mAdvertise.startAdvertise(this);

            Toast.makeText(this, "START", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "START FAILED", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopAdvertise() {
        if (mAdvertise != null && getBluetoothAdapter() != null && getBluetoothAdapter().isEnabled()) {
            mAdvertise.stopAdvertise();
            mAdvertise = null;

            Toast.makeText(this, "STOP", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "STOP FAILED", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Bluetoothの有効化
     */
    private void enableBluetooth() {
        if (getBluetoothAdapter() == null) {
            return;
        }

        if (!getBluetoothAdapter().isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 0x101);
        }
    }

    private BluetoothAdapter getBluetoothAdapter() {
        return BluetoothAdapter.getDefaultAdapter();
    }

}
