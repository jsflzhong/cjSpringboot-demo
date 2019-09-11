package interfaceNew.normalInterface;

import org.springframework.stereotype.Component;

@Component
public class SuperInterface3Impl implements SuperInterface2 {

    @Override
    public void defaultFunction() {
        defaultFunction();
    }
}
