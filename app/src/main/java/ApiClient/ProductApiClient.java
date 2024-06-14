package ApiClient;

import ApiService.ProductApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductApiClient {
    private static final String BASE_URL =
            "http://10.0.2.2:8888/api/";
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
    public static ProductApiService getApiService() {
        return getRetrofitInstance().create(ProductApiService.class);
    }
}
