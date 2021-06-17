package ru.lab.businessLogic.services.logicServices.validators.commandValidators.impls;

import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.ArgumentValidator;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.exceptions.WrongArgumentException;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.exceptions.WrongCountOfArgumentsException;

import java.util.Map;
import java.util.Set;

public class ArgumentCommandValidator implements ArgumentValidator {

    private final Set<CommandArgs> validArgsTypes;

    public ArgumentCommandValidator(Set<CommandArgs> validArgsTypes) {
        this.validArgsTypes = validArgsTypes;
    }


    @Override
    public Boolean validateArgs(Map<CommandArgs, String> commandArgs) throws WrongCountOfArgumentsException, WrongArgumentException {
        if(commandArgs.isEmpty()){
            throw new WrongCountOfArgumentsException();
        }
        if (!validArgsTypes.containsAll(commandArgs.keySet())){
            throw new WrongArgumentException();
        }

        return true;
    }
}
