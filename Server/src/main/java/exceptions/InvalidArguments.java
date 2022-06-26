package exceptions;

public class InvalidArguments extends CommandException
{
    public InvalidArguments(String message)
    {
        super(message);
    }

    public InvalidArguments(String message, Throwable cause)
    {
        super(message, cause);
    }
}
