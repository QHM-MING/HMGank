package gank.qhm.com.hmgank.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qhm on 2017/4/24
 */

public class CategoryModel {

    public boolean error;
    public List<ResultsBean> results;

    public static class ResultsBean implements Serializable {
        public String _id;              //Id
        public String createdAt;        //创建时间
        public String desc;             //描述
        public String publishedAt;      //发布时间
        public String source;           //资源展示类型 (猜的)
        public String type;             //干货类属
        public String url;              //url路径
        public boolean used;            //不晓得干啥的
        public String who;              //干货提供人
        public List<String> images;     //效果组图
    }
}
