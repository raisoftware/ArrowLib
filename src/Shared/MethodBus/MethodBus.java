package Shared.MethodBus;

public interface MethodBus<ListenerType>
{
    @SuppressWarnings("unchecked")
    public ListenerType createPublisher( Object eventSource, Class<ListenerType> eventListenerClass );

    @SuppressWarnings("unchecked")
    public void subscribe(ListenerType listener);

    @SuppressWarnings("unchecked")
    public void unsubscribe(ListenerType listener);

//    public void setExceptionHandler(Thread.UncaughtExceptionHandler ex_handler);
}
