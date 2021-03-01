package zephyr.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zephyr on 2019-07-14.
 */
@SpringBootApplication
@RestController
@RibbonClient(name = "myClient") // , configuration = RibbonConfig.class
public class TreasureBoxRibbonApp {

    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(TreasureBoxRibbonApp.class, args);
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/hi")
    public String hi(@RequestParam(value="name", defaultValue="Artaban") String name) {
        String greeting = this.restTemplate.getForObject("http://myClient/greeting", String.class);
        return String.format("%s, %s!", greeting, name);
    }

}
