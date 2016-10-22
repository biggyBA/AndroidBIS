package ba.biggy.androidbis.retrofitInterface;


import ba.biggy.androidbis.POJO.retrofitServerObjects.SparepartServerResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SparepartRequestInterface {

    @GET("AndroidBIS/spareparts/getSpareparts.php")
    Call<SparepartServerResponse> getSparepart();

}
