package ru.otus2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ioc {
    static TestLoggingInterface createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myLogClass;
        private final Set<Method> logMethods;

        DemoInvocationHandler(TestLoggingInterface myLogClass) {
            this.myLogClass = myLogClass;
            logMethods = Stream.of(myLogClass.getClass().getMethods())
                    .filter(method -> method.getDeclaredAnnotation(Log.class) != null)
                    .collect(Collectors.toSet());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (logMethods.stream()
                    .anyMatch(m -> method.getName().equals(m.getName()) &&
                            Arrays.equals(m.getParameterTypes(), method.getParameterTypes()))) {
                System.out.println("executed method: calculation, param: " + Arrays.toString(args));
            }
            return method.invoke(myLogClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myLogClass=" + myLogClass +
                    '}';
        }
    }
}
