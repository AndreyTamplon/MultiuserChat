package exceptions;

public class CommandException extends ControllerException
{
    public CommandException(String message)
    {
        super(message);
    }

    public CommandException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
