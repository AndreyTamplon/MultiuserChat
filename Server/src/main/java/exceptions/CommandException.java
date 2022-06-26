package exceptions;

public class CommandException extends CommandExecutorException
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
