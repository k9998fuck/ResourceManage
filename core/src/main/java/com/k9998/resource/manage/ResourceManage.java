package com.k9998.resource.manage;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResourceManage {

    public static final String TAG = "ResourceManage";

    private static final ResourceManage instance = new ResourceManage();

    public static ResourceManage getInstance() {
        return instance;
    }

    private ResourceManage() {
    }

    final Set<ResourceType<?>> resourceTypes = new HashSet<>();
    final List<Listener> listeners = new ArrayList<>();

    public void addListener(@NotNull Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(@NotNull Listener listener) {
        listeners.remove(listener);
    }

    public <T extends IResource> void registerResourceType(@NotNull ResourceType<T> resourceType, @Nullable List<IResourceAction<T>> resourceActions) {
        resourceType.resourceActions.clear();
        if (resourceActions != null) {
            resourceType.resourceActions.addAll(resourceActions);
        }
        resourceTypes.add(resourceType);
        for (Listener listener : listeners) {
            try {
                listener.change(new ArrayList<>(resourceTypes));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void unregisterResourceType(@NotNull ResourceType<?> resourceType) {
        resourceTypes.remove(resourceType);
        for (Listener listener : listeners) {
            try {
                listener.change(new ArrayList<>(resourceTypes));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public <T extends IResource> void addResource(@NotNull T resource) {
        for (ResourceType<?> resourceType : resourceTypes) {
            if (resourceType.clazz == resource.getClass()) {
                ResourceType<T> resourceType2 = (ResourceType<T>) resourceType;
                resourceType2.resources.add(resource);
                for (Listener listener : listeners) {
                    try {
                        listener.change(resourceType2, new ArrayList<>(resourceType2.resources));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    public <T extends IResource> void removeResource(@NotNull T resource) {
        for (ResourceType<?> resourceType : resourceTypes) {
            if (resourceType.clazz == resource.getClass()) {
                ResourceType<T> resourceType2 = (ResourceType<T>) resourceType;
                resourceType2.resources.remove(resource);
                for (Listener listener : listeners) {
                    try {
                        listener.change(resourceType2, new ArrayList<>(resourceType2.resources));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }

    public @NotNull List<ResourceType<?>> getResourceTypes() {
        return new ArrayList<>(resourceTypes);
    }

    public @NotNull <T extends IResource> List<T> getResources(@NotNull ResourceType<T> resourceType) {
        return new ArrayList<>(resourceType.resources);
    }

    public @NotNull <T extends IResource> List<IResourceAction<T>> getResourceAction(@NotNull T resource) {
        List<IResourceAction<T>> resourceActions = new ArrayList<>();
        for (ResourceType<?> resourceType : resourceTypes) {
            if (resourceType.clazz == resource.getClass()) {
                for (IResourceAction<?> resourceAction : resourceType.resourceActions) {
                    IResourceAction<T> resourceAction2 = (IResourceAction<T>) resourceAction;
                    resourceActions.add(new IResourceAction<T>(){

                        @Override
                        public T getResource() {
                            return resource;
                        }

                        @Override
                        public boolean isEnabled() {
                            boolean isEnabled;
                            synchronized (resourceAction2.lock){
                                resourceAction2.t = resource;
                                isEnabled = resourceAction2.isEnabled();
                                resourceAction2.t = null;
                            }
                            return isEnabled;
                        }

                        @Override
                        public String getName() {
                            String name;
                            synchronized (resourceAction2.lock){
                                resourceAction2.t = resource;
                                name = resourceAction2.getName();
                                resourceAction2.t = null;
                            }
                            return name;
                        }

                        @Override
                        public String getDescribe() {
                            String describe;
                            synchronized (resourceAction2.lock){
                                resourceAction2.t = resource;
                                describe = resourceAction2.getDescribe();
                                resourceAction2.t = null;
                            }
                            return describe;
                        }

                        @Override
                        public boolean execute() {
                            boolean result;
                            synchronized (resourceAction2.lock){
                                resourceAction2.t = resource;
                                result = resourceAction2.execute();
                                resourceAction2.t = null;
                            }
                            return result;
                        }
                    });
                }
                break;
            }
        }
        return resourceActions;
    }

    public interface Listener {

        void change(@NotNull List<ResourceType<?>> resourceTypes);

        <T extends IResource> void change(@NotNull ResourceType<T> resourceTypes, @NotNull List<T> resources);

        <T extends IResource> void change(@NotNull IResource resource, @NotNull List<IResourceAction<T>> resourceActions);

    }

}
