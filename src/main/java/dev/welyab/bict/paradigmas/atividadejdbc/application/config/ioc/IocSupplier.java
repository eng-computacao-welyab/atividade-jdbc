package dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc;

@FunctionalInterface
public interface IocSupplier {

    @SuppressWarnings("java:S112")
    Object get() throws Exception;
}
