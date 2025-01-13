package org.quickfixj.logger;

public class MarketMakingLogger {
    public Class<?> clazz;

    public MarketMakingLogger(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void info(String log) {
        System.out.printf("LOG(%s): %s%n", clazz.getName(), log );
    }

    public static MarketMakingLogger create(Class<?> clazz) {
        return new MarketMakingLogger(clazz);
    }
}
