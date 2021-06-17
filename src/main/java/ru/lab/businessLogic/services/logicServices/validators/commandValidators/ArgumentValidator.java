package ru.lab.businessLogic.services.logicServices.validators.commandValidators;

import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.exceptions.WrongArgumentException;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.exceptions.WrongCountOfArgumentsException;

import java.util.Map;


/**
 * валидаторы для аргументов команды
 */
public interface ArgumentValidator {
    Boolean validateArgs(Map<CommandArgs, String> commandArgs)
            throws WrongCountOfArgumentsException, WrongArgumentException;
}
