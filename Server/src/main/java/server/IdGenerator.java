package server;

public class IdGenerator
{
    private long lastId;

    public IdGenerator()
    {
        lastId = 0;
    }

    public long getNewId()
    {
        lastId++;
        return lastId;
    }
}