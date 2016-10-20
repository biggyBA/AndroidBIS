package ba.biggy.androidbis.retrofitInterface;


import java.util.List;

import ba.biggy.androidbis.POJO.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserRequestInterface {

    @GET("AndroidBIS/users/getUsers.php")
    Call<List<User>> getAllUsers();

}
