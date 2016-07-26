package br.eti.app.myandroidapplication;

/**
 * Created by Administrador on 26/07/2016.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

public class LocalizacaoActivity extends Activity {
    private LocationManager locationManager;
    private TextView latitude, longitude, provedor;


        private String urlBase = "http://maps.googleapis.com/maps/api/staticmap" +
                "?size=400x400&sensor=true&markers=color:red|%s,%s";
        private WebView mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localizacao);

        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        provedor = (TextView) findViewById(R.id.provedor);
        mapa = (WebView) findViewById(R.id.mapa);

        locationManager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);

        Listener listener = new Listener();

        long tempoAtualizacao = 0;
        float distancia = 0;

        // requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},8);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            Log.d("Sergio: ", "DENTRO DO IF");

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 8);
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 9);


            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                tempoAtualizacao, distancia, listener);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                tempoAtualizacao, distancia, listener);


    }

    private class Listener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            String latitudeStr = String.valueOf(location.getLatitude());
            String longitudeStr = String.valueOf(location.getLongitude());

            provedor.setText(location.getProvider());
            latitude.setText(latitudeStr);
            longitude.setText(longitudeStr);

            String url = String.format(urlBase, latitudeStr, longitudeStr);
            mapa.loadUrl(url);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}