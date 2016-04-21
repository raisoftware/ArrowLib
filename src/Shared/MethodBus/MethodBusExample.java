package Shared.MethodBus;

import Shared.MethodBus.Implementation.SyncMethodBus;

public class MethodBusExample
{

    public static void main(String[] args) throws Throwable
    {
        MethodBusExample eventSource = new MethodBusExample();

        MethodBus eventBus = new SyncMethodBus();
        SignalType subscriber1 = new SignalType()
        {
            public void start()
            {
                System.out.println("start1");
            }

            public void stop()
            {
                System.out.println("stop1");
            }
        };
        eventBus.subscribe(subscriber1);
        eventBus.subscribe(subscriber1);
        
        eventBus.subscribe(new SignalType()
        {
            public void start()
            {
                System.out.println("start2");
            }

            public void stop()
            {
                System.out.println("stop2");
            }
        });

        SignalType publisher = (SignalType) eventBus.createPublisher(eventSource, SignalType.class);
        System.out.println("fire start()");
        publisher.start();

        System.out.println("fire start()");
        publisher.start();

        System.out.println("fire stop()");
        publisher.stop();

    }

    public static interface SignalType
    {
        void start();
        void stop();
    }

}
