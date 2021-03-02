package zephyr.reactor;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import zephyr.reactor.domain.User;

import java.util.concurrent.CompletableFuture;

/**
 * Learn how to adapt from/to RxJava 2 Observable/Single/Flowable and Java 8+ CompletableFuture.
 *
 * Mono and Flux already implements Reactive Streams interfaces so they are natively
 * Reactive Streams compliant + there are {@link Mono#from(Publisher)} and {@link Flux#from(Publisher)}
 * factory methods.
 *
 * For RxJava 2, you should not use Reactor Adapter but only RxJava 2 and Reactor Core.
 *
 * @author Sebastien Deleuze
 */
public class Part09Adapt {

//========================================================================================

    // Adapt Flux to RxJava Flowable
    Flowable<User> fromFluxToFlowable(Flux<User> flux) {
        return Flowable.fromPublisher(flux);
    }

    // Adapt RxJava Flowable to Flux
    Flux<User> fromFlowableToFlux(Flowable<User> flowable) {
        return Flux.from(flowable);
    }

//========================================================================================

    // Adapt Flux to RxJava Observable
    Observable<User> fromFluxToObservable(Flux<User> flux) {
        return Observable.fromPublisher(flux);
    }

    // Adapt RxJava Observable to Flux
    Flux<User> fromObservableToFlux(Observable<User> observable) {
        return Flux.from(observable.toFlowable(BackpressureStrategy.BUFFER));
    }

//========================================================================================

    // Adapt Mono to RxJava Single
    Single<User> fromMonoToSingle(Mono<User> mono) {
        return Single.fromPublisher(mono);
    }

    // Adapt RxJava Single to Mono
    Mono<User> fromSingleToMono(Single<User> single) {
        return Mono.from(single.toFlowable());
    }

//========================================================================================

    // Adapt Mono to Java 8+ CompletableFuture
    CompletableFuture<User> fromMonoToCompletableFuture(Mono<User> mono) {
        return mono.toFuture();
    }

    // Adapt Java 8+ CompletableFuture to Mono
    Mono<User> fromCompletableFutureToMono(CompletableFuture<User> future) {
        return Mono.fromFuture(future);
    }

}
