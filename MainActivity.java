// MainActivity.java
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String CURRENT_VERSION = "1.0.0.00";
    private ApiService apiService;
    private TextView onlineDevicesCount;
    private TextView appVersionView;
    private Button connectButton;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onlineDevicesCount = findViewById(R.id.online_devices_count);
        appVersionView = findViewById(R.id.app_version);
        connectButton = findViewById(R.id.connect_button);
        updateButton = findViewById(R.id.update_button);

        Retrofit retrofit = RetrofitClient.getClient("http://45.236.162.3:3000");  // Atualize para o IP correto do servidor
        apiService = retrofit.create(ApiService.class);

        checkAppVersion();
        updateOnlineDevicesCount();

        connectButton.setOnClickListener(view -> {
            // Lógica para conectar dispositivos
        });

        updateButton.setOnClickListener(view -> {
            checkAppVersion();
        });
    }

    private void checkAppVersion() {
        apiService.getAppVersion().enqueue(new Callback<AppVersion>() {
            @Override
            public void onResponse(Call<AppVersion> call, Response<AppVersion> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AppVersion appVersion = response.body();
                    if (!CURRENT_VERSION.equals(appVersion.getVersion())) {
                        appVersionView.setText("Versão: " + CURRENT_VERSION + " (Atualização disponível: " + appVersion.getVersion() + ")");
                        updateButton.setVisibility(View.VISIBLE);
                        updateButton.setOnClickListener(view -> {
                            downloadAndInstallApk(appVersion.getUpdate_url());
                        });
                    } else {
                        appVersionView.setText("Versão: " + CURRENT_VERSION + " (Atualizado)");
                        updateButton.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<AppVersion> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro ao verificar a versão do aplicativo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateOnlineDevicesCount() {
        apiService.getDevices().enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Device> devices = response.body();
                    onlineDevicesCount.setText("Dispositivos online: " + devices.size());
                }
            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro ao obter dispositivos online", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private