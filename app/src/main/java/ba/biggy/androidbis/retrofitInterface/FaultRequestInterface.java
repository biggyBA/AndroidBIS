package ba.biggy.androidbis.retrofitInterface;


import ba.biggy.androidbis.POJO.retrofitServerObjects.FaultServerResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FaultRequestInterface {

    @GET("AndroidBIS/faults/getFaults.php")
    Call<FaultServerResponse> getFaults();
}
