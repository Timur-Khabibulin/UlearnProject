package org.example.common;

import lombok.Getter;

import java.lang.Throwable;
import java.util.function.Consumer;

public abstract class Result<T> {

    @Getter
    public static final class Success<T> extends Result<T> {
        private final T value;

        public Success(T value) {
            this.value = value;
        }
    }

    @Getter
    public static final class Failure<T> extends Result<T> {
        private final Throwable error;

        public Failure(Throwable error) {
            this.error = error;
        }
    }

    public static <T> Result<T> callAndGet(ThrowingSupplier<T> supplier) {
        try {
            return new Result.Success<>(supplier.get());
        } catch (Exception e) {
            return new Result.Failure<>(e);
        }
    }

    public boolean isSuccess() {
        return this instanceof Success;
    }

    public Success<T> asSuccess() {
        return (Success<T>) this;
    }

    public Result<T> ifSuccess(Consumer<T> action) {
        if (this.isSuccess())
            action.accept(this.asSuccess().value);
        return this;
    }

    public boolean isFailure() {
        return this instanceof Failure;
    }

    public Failure<?> asFailure() {
        return (Failure<?>) this;
    }

    public void ifFailure(Consumer<Throwable> action) {
        if (this.isFailure())
            action.accept(this.asFailure().error);
    }
}


