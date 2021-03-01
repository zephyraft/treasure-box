package zephyr.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by zephyr on 2020/3/30.
 */
@Configuration
public class ProfileAnnotationDemo {

    @Profile("!prod")
    @Bean
    public ProfileAnnotationBean devBean() {
        return new ProfileAnnotationBean("dev");
    }

    @Profile("prod")
    @Bean
    public ProfileAnnotationBean prodBean() {
        return new ProfileAnnotationBean("prod");
    }

    public static class ProfileAnnotationBean {

        private final String value;

        public ProfileAnnotationBean(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
