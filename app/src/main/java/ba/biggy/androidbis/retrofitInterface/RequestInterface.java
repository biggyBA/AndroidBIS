package ba.biggy.androidbis.retrofitInterface;


import ba.biggy.androidbis.POJO.ServerRequest;
import ba.biggy.androidbis.POJO.ServerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("test/index.php")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
