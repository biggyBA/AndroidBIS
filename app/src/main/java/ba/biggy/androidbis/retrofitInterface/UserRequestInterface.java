package ba.biggy.androidbis.retrofitInterface;


import ba.biggy.androidbis.POJO.retrofitServerObjects.UserServerResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserRequestInterface {

    @GET("AndroidBIS/users/getUsers.php")
    Call<UserServerResponse> getUser();


}
