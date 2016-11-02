package ba.biggy.androidbis.retrofitInterface;


import ba.biggy.androidbis.POJO.retrofitServerObjects.DeleteFaultServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.DeleteFaultServerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DeleteFaultRequestInterface {

    @POST("AndroidBIS/faults/deleteFault.php")
    Call<DeleteFaultServerResponse> operation(@Body DeleteFaultServerRequest request);


}
