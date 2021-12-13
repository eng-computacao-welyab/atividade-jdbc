package dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a very simple IoC like implementation.
 */
public class Ioc {

    private static final Map<String, ComponentSupplier> components = new HashMap<>();

    private Ioc() {
    }

    public static <E> E instance(Class<E> type, String componentName) {
        return type.cast(instance(componentName));
    }

    public static <E> E instance(String componentName) {
        Preconditions.checkNotNull(componentName, "componentName");
        var componentSupplier = components.get(componentName);
        if (componentSupplier == null) {
            throw new IocException(String.format("Component with name %s was not found", componentName));
        }
        try {
            //noinspection unchecked
            return (E) componentSupplier.getComponent();
        } catch (Exception e) {
            throw new IocException(
                    String.format(
                            "Fail to instantiate retrieve component with name %s",
                            componentName
                    ),
                    e
            );
        }
    }

    public static void registerSingleInstance(String name, IocSupplier instanceFactory) {
        registerSingleInstance(name, false, instanceFactory);
    }

    public static void registerSingleInstance(String name, boolean override, IocSupplier instanceFactory) {
        register(name, true, override, instanceFactory);
    }

    public static void registerFactoryInstance(String name, IocSupplier instanceFactory) {
        registerFactoryInstance(name, false, instanceFactory);
    }

    public static void registerFactoryInstance(String name, boolean override, IocSupplier instanceFactory) {
        register(name, false, override, instanceFactory);
    }

    private static void register(String name, boolean single, boolean override, IocSupplier instanceFactory) {
        if (components.containsKey(name) && !override) {
            throw new IocException(String.format("A component with name %s was already registered", name));
        }

        ComponentSupplier componentSupplier = single
                ? new SingleComponentSupplier(instanceFactory)
                : new FactoryComponentSupplier(instanceFactory);
        components.put(name, componentSupplier);
    }

    private static void clear() {
        components.clear();
    }

    private interface ComponentSupplier {
        Object getComponent() throws Exception;
    }

    private static class SingleComponentSupplier implements ComponentSupplier {

        final IocSupplier instanceFactory;
        Object component;

        SingleComponentSupplier(IocSupplier instanceFactory) {
            this.instanceFactory = instanceFactory;
        }

        @Override
        public Object getComponent() throws Exception {
            if (component == null) {
                component = instanceFactory.get();
            }
            return component;
        }
    }

    private static class FactoryComponentSupplier implements ComponentSupplier {

        final IocSupplier instanceFactory;

        FactoryComponentSupplier(IocSupplier instanceFactory) {
            this.instanceFactory = instanceFactory;
        }

        @Override
        public Object getComponent() throws Exception {
            return instanceFactory.get();
        }
    }
}
