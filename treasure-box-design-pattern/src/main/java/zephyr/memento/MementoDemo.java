package zephyr.memento;

public class MementoDemo {
    public static void main(String[] args) throws InterruptedException {
        Game game = new Game(100);
        Memento memento = game.createMemento();
        for (int i = 0; i < 100; i++) {
            System.out.println("==== " + i);
            System.out.println("当前状态:" + game);
            game.bet();
            System.out.println("所持金钱" + game.getMoney());
            // 决定如何处理memento
            if (game.getMoney() > memento.getMoney()) {
                System.out.println(" 所持金钱增加，保存");
                memento = game.createMemento();
            } else if (game.getMoney() < memento.getMoney() / 2) {
                System.out.println(" 所持金钱减少，因此恢复");
                game.restoreMemento(memento);
            }
            Thread.sleep(1000L);
        }

    }
}
