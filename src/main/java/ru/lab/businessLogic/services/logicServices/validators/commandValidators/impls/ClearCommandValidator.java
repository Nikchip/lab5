package ru.lab.businessLogic.services.logicServices.validators.commandValidators.impls;

import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.ArgumentValidator;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.exceptions.WrongArgumentException;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.exceptions.WrongCountOfArgumentsException;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ClearCommandValidator implements ArgumentValidator {
    @Override
    public Boolean validateArgs(Map<CommandArgs, String> commandArgs) throws WrongCountOfArgumentsException, WrongArgumentException {
        if (!commandArgs.isEmpty()){
            throw new WrongCountOfArgumentsException();
        }
        return true;
    }
}
