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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppComponentsContainerImpl implements AppComponentsContainer {
    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

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
        Set<Method> methods = Stream.of(configClass.getMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .collect(Collectors.toSet());
        for (Method method : methods) {
            List<Object> args = fillArgs(method);
            createBean(method, args.toArray(), configClass);
        }
    }

    private List<Object> fillArgs(Method method) {
        Class<?>[] methodParameters = method.getParameterTypes();
        List<Object> args = new ArrayList<>();
        for (Class<?> parameter : methodParameters) {
            for (Object appComponent : appComponents) {
                if (parameter.isInstance(appComponent)) {
                    args.add((parameter).cast(appComponent));
                }
            }
        }
        return args;
    }

    private void createBean(Method method, Object[] args, Class<?> configClass) {
        try {
            Object bean = method.invoke(configClass.getConstructors()[0].newInstance(), args);
            appComponents.add(bean);
            appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), bean);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
                .filter(componentClass::isInstance)
                .map(componentClass::cast)
                .findFirst()
                .orElse(null);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
