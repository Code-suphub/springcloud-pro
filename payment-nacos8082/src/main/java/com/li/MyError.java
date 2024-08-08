package com.li;

public class MyError extends Error {
    private static final long serialVersionUID = 1L;

    public MyError(String msg) {
        super(msg);
    }

    public MyError(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MyError(Throwable cause) {
        super(cause);
    }
}

interface Generator<T> {
    public T method();
}

class GeneratorImpl<T> implements Generator<T> {
    @Override
    public T method() {
        return null;
    }
}

class GeneratorImpl2<T> implements Generator<String> {
    @Override
    public String method() {
        return "hello";
    }
}