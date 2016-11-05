package ba.biggy.androidbis.retrofitInterface;


import ba.biggy.androidbis.POJO.retrofitServerObjects.AddFaultServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.AddFaultServerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddFaultRequestInterface {

    @POST("AndroidBIS/faults/addFault.php")
    Call<AddFaultServerResponse> operation(@Body AddFaultServerRequest request);
}
