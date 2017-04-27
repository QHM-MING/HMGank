package gank.qhm.com.hmgank.Dao;


import gank.qhm.com.hmgank.Model.CategoryModel;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by qhm on 2017/4/13
 */

public interface HMGankApi {

    /**
     * 根据不同品类获取数据
     * @param category  品类
     * @param limit 每次请求数据量 Max 为50 (干货限制)
     * @param page  页数
     */
    @GET("data/{category}/{limit}/{page}")
    Observable<CategoryModel> getCategoryDate(@Path("category") String category,
                                              @Path("limit") int limit,
                                              @Path("page") int page);
}
