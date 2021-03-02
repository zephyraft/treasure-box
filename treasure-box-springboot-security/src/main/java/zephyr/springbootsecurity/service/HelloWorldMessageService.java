package zephyr.springbootsecurity.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
public class HelloWorldMessageService {

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<String> findMessage(Mono<Principal> principal) {
        return principal.map(Principal::getName).map(it -> "Hello World!" + it);
    }

}
