package ba.biggy.androidbis.retrofitInterface;


import ba.biggy.androidbis.POJO.retrofitServerObjects.UploadServicesheetServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.UploadServicesheetServerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UploadServicesheetRequestInterface {

    @POST("AndroidBIS/servicesheets/insertServicesheet.php")
    Call<UploadServicesheetServerResponse> operation(@Body UploadServicesheetServerRequest request);
}
