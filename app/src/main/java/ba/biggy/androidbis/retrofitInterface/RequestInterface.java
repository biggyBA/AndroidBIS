package ba.biggy.androidbis.retrofitInterface;


import ba.biggy.androidbis.POJO.retrofitServerObjects.LoginServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.LoginServerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("test/login.php")
    Call<LoginServerResponse> operation(@Body LoginServerRequest request);

}
