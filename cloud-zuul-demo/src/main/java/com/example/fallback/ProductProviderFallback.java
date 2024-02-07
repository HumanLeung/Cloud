
package com.example.fallback;


import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author Administrator
 */
@Component
public class ProductProviderFallback implements FallbackProvider {


    /**
     * return - 返回 fallback 处理哪一个服务，返回的是服务的名称。
     * 推荐 - 为指定的服务定义特性化的fallback逻辑。
     * 推荐 - 提供一个处理所有服务的fallback逻辑
     * 好处 - 某个服务发生超时，那么指定的fallback逻辑执行，如果有新服务上线，未提供fallback逻辑，一个通用的。
     * @return
     */
    @Override
    public String getRoute() {
        return "cloud-product-service";
    }

    @Override
    public ClientHttpResponse fallbackResponse( String route, Throwable cause) {
        return new ClientHttpResponse() {
            /**
             * @return
             */
            @Override
            @NonNull
            public HttpStatus getStatusCode() {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR.value();
            }

            @Override
            @NonNull
            public String getStatusText() {
                return "ERROR";
            }

            @Override
            public void close() {
                // TODO document why this method is empty
            }

            @Override
            @NonNull
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("{\"message\":\":\"商品服务不可用 \"}".getBytes());
            }

            /**
             * @return
             */
            @Override
            @NonNull
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(new MediaType("application","json", StandardCharsets.UTF_8));
                return headers;
            }
        };
    }
}
