package ApiService;

import java.util.List;

import Models.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ProductApiService {
    @GET("Product")
    Call<List<Product>> gets();
    @GET("Product/{id}")
    Call<List<Product>> get();
    @POST("Product")
    Call<Product> create(@Body Product obj);

    @PUT("Product/{id}")
    Call<Product> update(@Path("id") int id, @Body Product obj);

    @DELETE("Product/{id}")
    Call<Void> delete(@Path("id") int id);
}
