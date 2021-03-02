package zephyr.okhttp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Spider {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //设置为兼容模式
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS).build();
        OkHttpClient client = new OkHttpClient.Builder().connectionSpecs(Collections.singletonList(spec)).build();

        for (int pageindex = 1; pageindex < 1000; pageindex++) {

            Map<String, String> param = new HashMap<>();
            param.put("sourceid", "0");
            param.put("queryparams", "||");
            param.put("pageindex", String.valueOf(pageindex));
            param.put("pagesize", "10");
            RequestBody body = RequestBody.create(null, objectMapper.writeValueAsBytes(param));

            Request request = new Request.Builder()
                    .url("https://kf.cdzfgjj.chengdu.gov.cn:9701/WebInfoManagement/MessageHandleService.aspx/GetMessageList")
                    .post(body)
                    .addHeader("Content-Type", "application/json; charset=gb2312")
                    .build();
            Response response = client
                    .newCall(request)
                    .execute();

            String responseString = response.body().string();
            Map<String, String> map = objectMapper.readValue(responseString, new TypeReference<Map<String, String>>() {
            });
            final String data = map.get("d");
            if (data.contains("个人")) {
                System.out.println(data);
            }
        }
    }

}
