package com.gentaliti.common.lock;

@FunctionalInterface
public interface AtomicFunction<T> {
    T apply();
}
