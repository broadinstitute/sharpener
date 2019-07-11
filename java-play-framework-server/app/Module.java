import com.google.inject.AbstractModule;

import controllers.*;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(SharpenerApiControllerImpInterface.class).to(SharpenerApiControllerImp.class);
    }
}