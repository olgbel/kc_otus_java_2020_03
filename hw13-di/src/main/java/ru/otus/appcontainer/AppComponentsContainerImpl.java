package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

public class AppComponentsContainerImpl implements AppComponentsContainer {
    private final Logger logger = LoggerFactory.getLogger(AppComponentsContainerImpl.class);

    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<Class<?>, Object> appComponentsByType = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    public AppComponentsContainerImpl(String packageName) {
        Reflections reflections = new Reflections(packageName);
        reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class)
                .stream()
                .sorted(Comparator.comparingInt(config -> config.getAnnotation(AppComponentsContainerConfig.class).order()))
                .forEach(this::processConfig);
    }

    public AppComponentsContainerImpl(Class<?>... initialConfigClass) {
        Stream.of(initialConfigClass)
                .sorted(Comparator.comparingInt(configClass -> configClass.getAnnotation(AppComponentsContainerConfig.class).order()))
                .forEach(this::processConfig);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        createBeans(configClass);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private void createBeans(Class<?> configClass) {
        Stream.of(configClass.getMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(config -> config.getAnnotation(AppComponent.class).order()))
                .forEach(method -> {
                    List<Object> args = fillArgs(method);
                    createBean(method, args.toArray(), configClass);
                });
    }

    private List<Object> fillArgs(Method method) {
        Class<?>[] methodParameters = method.getParameterTypes();
        List<Object> args = new ArrayList<>();
        Stream.of(methodParameters)
                .forEach(parameter -> args.add(appComponentsByType.get(parameter)));
        return args;
    }

    private void createBean(Method method, Object[] args, Class<?> configClass) {
        try {
            Object bean = method.invoke(configClass.getConstructor().newInstance(), args);
            appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), bean);
            appComponentsByType.put(bean.getClass(), bean);
            Stream.of(bean.getClass().getInterfaces())
                    .forEach(beanInterface -> appComponentsByType.put(beanInterface, bean));
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponentsByType.get(componentClass);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
