package projetopgm.com.br.projetopgm.webservice;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

interface IRetroService {
    @Multipart
    @POST("/Foto/uploadImage")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part filePart);
}
