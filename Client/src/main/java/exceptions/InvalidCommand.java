package exceptions;


public class InvalidCommand extends ControllerException
{
    public InvalidCommand(String message)
    {
        super(message);
    }

    public InvalidCommand(String message, Throwable cause)
    {
        super(message, cause);
    }
}
