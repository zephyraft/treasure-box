package zephyr.reactor;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import zephyr.reactor.domain.User;
import zephyr.reactor.repository.ReactiveRepository;
import zephyr.reactor.repository.ReactiveUserRepository;

/**
 * Learn how to control the demand.
 *
 * @author Sebastien Deleuze
 */
public class Part06Request {

    ReactiveRepository<User> repository = new ReactiveUserRepository();

//========================================================================================

    // Create a StepVerifier that initially requests all values and expect 4 values to be received
    StepVerifier requestAllExpectFour(Flux<User> flux) {
        return StepVerifier.create(flux).expectNextCount(4).expectComplete();
    }

//========================================================================================

    // Create a StepVerifier that initially requests 1 value and expects User.SKYLER then requests another value and expects User.JESSE.
    StepVerifier requestOneExpectSkylerThenRequestOneExpectJesse(Flux<User> flux) {
        return StepVerifier.create(flux)
                .thenRequest(1).expectNextMatches(user -> user.equals(User.SKYLER))
                .thenRequest(1).expectNextMatches(user -> user.equals(User.JESSE))
                .thenCancel();
    }

//========================================================================================

    // Return a Flux with all users stored in the repository that prints automatically logs for all Reactive Streams signals
    Flux<User> fluxWithLog() {
        return repository.findAll().log();
    }

//========================================================================================

    // Return a Flux with all users stored in the repository that prints "Starring:" on subscribe, "firstname lastname" for all values and "The end!" on complete
    Flux<User> fluxWithDoOnPrintln() {
        return repository.findAll()
                .doOnSubscribe(t -> System.out.println("Starring:"))
                .doOnNext(t -> System.out.println(t.getFirstname() + " " + t.getLastname()))
                .doOnComplete(() -> System.out.println("The end!"));
    }

}
