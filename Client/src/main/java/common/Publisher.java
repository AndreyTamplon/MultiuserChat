package common;

import java.util.ArrayList;

public class Publisher
{
    private final ArrayList<Subscriber> subscribers;

    public Publisher(ArrayList<Subscriber> subscribers)
    {
        this.subscribers = subscribers;
    }

    public Publisher()
    {
        this.subscribers = new ArrayList<>();
    }

    public void notifySubscriber(Subscriber subscriber)
    {
        subscriber.update(this);
    }

    protected void notifySubscribers()
    {
        for (final Subscriber subscriber : subscribers)
        {
            notifySubscriber(subscriber);
        }
    }

    public void notifySubscriber(Subscriber subscriber, Publisher publisher)
    {
        subscriber.update(publisher);
    }

    protected void notifySubscribers(Publisher publisher)
    {
        for (final Subscriber subscriber : subscribers)
        {
            notifySubscriber(subscriber, publisher);
        }
    }

    public synchronized void subscribe(Subscriber subscriber)
    {
        if (subscriber == null)
        {
            throw new NullPointerException("null is passed in the subscriber field");
        }
        if (!subscribers.contains(subscriber))
        {
            subscribers.add(subscriber);
        }
    }

    public synchronized void unsubscribe(Subscriber subscriber)
    {
        if (subscriber == null)
        {
            throw new NullPointerException("null is passed in the subscriber field");
        }
        if (!subscribers.contains(subscriber))
        {
            throw new IllegalArgumentException("Unknown subscriber: " + subscriber);
        }
        subscribers.remove(subscriber);
    }
}
