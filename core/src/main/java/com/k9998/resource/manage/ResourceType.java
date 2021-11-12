package com.k9998.resource.manage;

import java.util.ArrayList;
import java.util.List;

public class ResourceType<T extends IResource> {

    final String name;
    final Class<T> clazz;
    protected final List<T> resources = new ArrayList<>();
    protected final List<IResourceAction<T>> resourceActions = new ArrayList<>();

    public ResourceType(String name, Class<T> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ResourceType<?> && clazz.equals(((ResourceType<?>) obj).clazz);
    }

    @Override
    public int hashCode() {
        return clazz.hashCode();
    }

}
