package ba.biggy.androidbis.retrofitInterface;


import ba.biggy.androidbis.POJO.retrofitServerObjects.UploadSparepartServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.UploadSparepartServerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UploadSparepartsRequestInterface {

    @POST("AndroidBIS/servicesheets/insertServicesheet.php")
    Call<UploadSparepartServerResponse> operation(@Body UploadSparepartServerRequest request);
}
