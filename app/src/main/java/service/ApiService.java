package service;

import com.joybar.libupdate.data.UpdateInfo;

import io.reactivex.Observable;
import me.joybar.superwifi.data.BaseResult;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by joybar on 2018/1/8.
 */

public interface ApiService {

	@GET("updateinfo/")
	Observable<BaseResult<UpdateInfo>> getUpdateInfo();

	@GET("updateinfo/")
	Call<BaseResult<UpdateInfo>> getUpdateInfo1();

	@GET("/")
	Call<String> getBaidu();


}
