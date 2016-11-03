package ba.biggy.androidbis.retrofitInterface;



import ba.biggy.androidbis.POJO.retrofitServerObjects.UpdateFaultServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.UpdateFaultServerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UpdateFaultRequestInterface {

    @POST("AndroidBIS/faults/updateFault.php")
    Call<UpdateFaultServerResponse> operation(@Body UpdateFaultServerRequest request);
}
