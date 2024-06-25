// ApiService.java
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("/devices")
    Call<List<Device>> getDevices();

    @POST("/devices")
    Call<Void> updateDevice(@Body Device device);

    @POST("/requests")
    Call<Void> addRequest(@Body Request request);

    @GET("/app_version")
    Call<AppVersion> getAppVersion();
}