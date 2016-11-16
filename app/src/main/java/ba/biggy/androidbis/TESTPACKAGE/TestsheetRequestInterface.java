package ba.biggy.androidbis.TESTPACKAGE;


import retrofit2.Call;
import retrofit2.http.GET;

public interface TestsheetRequestInterface {

    @GET("AndroidBIS/archive/getTestArchive.php")
    Call<TestsheetServerResponse> getFaults();
}
