package controller;

import client.ClientTerminal;
import exceptions.CommandException;

public interface Command
{
    void execute(ClientTerminal clientTerminal, String... arguments) throws CommandException;
}
