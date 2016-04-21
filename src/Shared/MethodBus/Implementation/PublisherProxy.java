package Shared.MethodBus.Implementation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PublisherProxy<T> implements InvocationHandler
{
    private final SyncMethodBus eventBus;
    private final Object eventSource;
    private final Class<T> eventListenerClass;

    public PublisherProxy(SyncMethodBus eventBus, Object eventSource, Class<T> eventListenerClass)
    {
        this.eventBus = eventBus;
        this.eventSource = eventSource;
        this.eventListenerClass = eventListenerClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable
    {
        eventBus.publishEvent(new MethodCall(eventSource, eventListenerClass, method, arguments));
        return null;
    }

}
