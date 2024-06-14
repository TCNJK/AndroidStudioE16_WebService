package ApiClient;

import ApiService.PostApiService;
import Models.Post;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostApiClient {
    private static final String BASE_URL =
            "https://jsonplaceholder.typicode.com/";
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static PostApiService getApiService() {
        return getRetrofitInstance().create(PostApiService.class);
    }
}
