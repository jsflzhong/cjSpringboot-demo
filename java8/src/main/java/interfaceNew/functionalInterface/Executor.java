package interfaceNew.functionalInterface;

public class Executor<T> {

    public static void doIfTrue(boolean var1, Processor var2) {
        if(var1) {
            var2.process();
        }
    }

    public static void doAfterCheck(boolean var1,Processor var2, Processor var3) {
        if(var1) {
            var2.process();
        } else {
            var3.process();
        }
    }

     T produceIfTrue(boolean var1, ProducerProcessor<T> var2) {
        if(var1) {
            return var2.process();
        }
        return null;
    }

    T produceAfterCheck(boolean var1,ProducerProcessor<T> var2, ProducerProcessor<T> var3) {
        if(var1) {
            return var2.process();
        } else {
            return var3.process();
        }
    }

    @FunctionalInterface
    public interface Processor {

        void process();
    }

    @FunctionalInterface
    public interface ProducerProcessor<T> {

        T process();
    }
}
