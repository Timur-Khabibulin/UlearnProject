package org.example.common;

@FunctionalInterface
public interface ThrowingSupplier<R> {
    R get() throws Exception;
}

