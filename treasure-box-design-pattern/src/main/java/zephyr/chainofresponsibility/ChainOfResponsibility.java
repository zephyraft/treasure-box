package zephyr.chainofresponsibility;

public class ChainOfResponsibility {

    public static void main(String[] args) {
        final Support alice = new NoSupport("Alice");
        final Support bob = new LimitSupport("Bob", 100);
        final Support charlie = new SpecialSupport("charlie", 429);
        final Support diana = new LimitSupport("Diana", 200);
        final Support elmo = new OddSupport("Elmo");
        final Support fred = new LimitSupport("Fred", 300);

        alice.setNext(bob).setNext(charlie).setNext(diana).setNext(elmo).setNext(fred);
        for (int i = 0; i < 500; i += 33) {
            alice.support(new Trouble(i));
        }
    }

}
