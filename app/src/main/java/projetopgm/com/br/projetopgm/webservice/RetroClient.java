package projetopgm.com.br.projetopgm.webservice;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import projetopgm.com.br.projetopgm.base.Foto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetroClient {

    private static final String ROOT_URL = "localhost:3461";


    public static void uploadImage(List<Foto> fotos){

        if(fotos.size() == 0)
            return;

        //HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); .addInterceptor(interceptor)
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();

        // Change base URL to your upload server URL.
        IRetroService service = new Retrofit.Builder().baseUrl("http://" + ROOT_URL + "/").client(client).build().create(IRetroService.class);

        String filePath = fotos.get(0).getArquivo();

        File file = new File(filePath);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        //RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

        Call<ResponseBody> req = service.uploadImage(body);
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response != null){

                }
                // Do Something
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
