package interfaceNew.functionalInterface;


import interfaceNew.Exception.CheckedException;

public interface JudgeAndExecute {

    default void run(Boolean condition, Processor processor) {
        if (condition) {
            processor.run();
        }
    }

    default void run(Boolean condition,
                     Processor processor1, Processor processor2) {
        if (condition) {
            processor1.run();
        } else {
            processor2.run();
        }
    }

    default void runWithE(Boolean condition, ProcessorWithE processor)
            throws CheckedException{
        if (condition) {
            processor.run();
        }
    }

    /**
     * 由于四大内置函数式接口都有返回值,或有参数, 而这个if/else接口同时不需要参数或返回值,
     * 所以需要额外自定义一个函数式接口.
     */
    @FunctionalInterface
    interface Processor {
        void run();
    }

    /**
     * 由于调用这个函数接口的方法所传入的参数方法中,可能会向上层抛出异常,
     * 所以调用的函数接口interface本身的抽象方法就需要抛出异常.
     */
    @FunctionalInterface
    interface ProcessorWithE {
        void run() throws CheckedException;
    }
}
