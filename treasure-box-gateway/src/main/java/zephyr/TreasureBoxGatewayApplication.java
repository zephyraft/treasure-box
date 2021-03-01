package zephyr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@SpringBootApplication
public class TreasureBoxGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TreasureBoxGatewayApplication.class, args);
    }

    @GetMapping("/test")
    public Mono<ResponseEntity<byte[]>> proxy(ProxyExchange<byte[]> proxy) {
        return proxy.uri("https://m.ximalaya.com/m-revision/page/album/queryAlbumPageActivityInfo/33513407").get();
    }

    @GetMapping("/bbb")
    public Mono<ResponseEntity<byte[]>> proxy3(ProxyExchange<byte[]> proxy) {
        return proxy.uri("/aaa").get();
    }

    @GetMapping("/aaa")
    public Mono<String> proxy2(ProxyExchange<byte[]> proxy) {
        return Mono.just("1234");
    }

}
