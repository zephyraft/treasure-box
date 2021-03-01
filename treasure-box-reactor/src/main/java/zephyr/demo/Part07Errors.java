package zephyr.demo;

import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import zephyr.demo.domain.User;

/**
 * Learn how to deal with errors.
 *
 * @author Sebastien Deleuze
 * @see Exceptions#propagate(Throwable)
 */
public class Part07Errors {

//========================================================================================

    // Return a Mono<User> containing User.SAUL when an error occurs in the input Mono, else do not change the input Mono.
    Mono<User> betterCallSaulForBogusMono(Mono<User> mono) {
        return mono.onErrorReturn(User.SAUL);
    }

//========================================================================================

    // Return a Flux<User> containing User.SAUL and User.JESSE when an error occurs in the input Flux, else do not change the input Flux.
    Flux<User> betterCallSaulAndJesseForBogusFlux(Flux<User> flux) {
        return flux.onErrorResume(t -> Flux.just(User.SAUL, User.JESSE));
    }

//========================================================================================

    // Implement a method that capitalizes each user of the incoming flux using the
    // #capitalizeUser method and emits an error containing a GetOutOfHereException error
    Flux<User> capitalizeMany(Flux<User> flux) {
        return flux.map(this::capitalizeUser);
    }

    User capitalizeUser(User user) {
        if (user.equals(User.SAUL)) {
            throw Exceptions.propagate(new GetOutOfHereException());
        }
        return new User(user.getUsername().toUpperCase(), user.getFirstname().toUpperCase(), user.getLastname().toUpperCase());
    }

    protected final class GetOutOfHereException extends Exception {
        private static final long serialVersionUID = 0L;
    }

}
