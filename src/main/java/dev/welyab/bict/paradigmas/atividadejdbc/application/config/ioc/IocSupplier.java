package dev.welyab.bict.paradigmas.atividadejdbc.application.config.ioc;

@FunctionalInterface
public interface IocSupplier {

    public Object get() throws Exception;
}
