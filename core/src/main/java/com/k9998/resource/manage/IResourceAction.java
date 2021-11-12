package com.k9998.resource.manage;

public abstract class IResourceAction<T extends IResource> {

    protected final Object lock = new Object();

    protected T t;

    public T getResource() {
        return t;
    }

    public abstract boolean isEnabled();

    public abstract String getName();

    public abstract String getDescribe();

    public abstract boolean execute();

}
