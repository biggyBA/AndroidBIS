package ba.biggy.androidbis.retrofitInterface;


import ba.biggy.androidbis.POJO.retrofitServerObjects.ArchiveFaultServerRequest;
import ba.biggy.androidbis.POJO.retrofitServerObjects.ArchiveFaultServerResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ArchiveFaultRequestInterface {

    @POST("AndroidBIS/faults/archiveFault.php")
    Call<ArchiveFaultServerResponse> operation(@Body ArchiveFaultServerRequest request);

}
