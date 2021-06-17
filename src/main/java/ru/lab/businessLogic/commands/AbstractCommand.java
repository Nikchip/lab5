package ru.lab.businessLogic.commands;

import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResult;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResultStatus;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.services.logicServices.validators.commandValidators.ArgumentValidator;
import java.util.Set;


/**
 * класс родитель для любых команд. Содержит базовый набор методов
 * и один абстрактный метод, который переопределяется определенной командой
 * (реализация паттерна *Команда*)
 */
public abstract class AbstractCommand {
    protected CommandType commandType;
    protected String commandName;
    protected String description;
    protected Set<CommandArgs> commandArgsSet;
    protected ArgumentValidator validator;

    public AbstractCommand(CommandType commandType,
                           String commandName,
                           Set<CommandArgs> commandArgsSet,
                           String description) {
        this.commandType = commandType;
        this.commandName = commandName;
        this.commandArgsSet = commandArgsSet;
        this.description = description;
    }

    public CommandResult run(Query query){
        try {
            return execute(query);
        } catch (Exception e) {
            return new CommandResult(
                    e.getMessage(),
                    CommandResultStatus.valueOf(e.toString())
            );
        }
    }

    public abstract CommandResult execute(Query query) throws Exception;

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public Set<CommandArgs> getCommandArgsSet() {
        return commandArgsSet;
    }

    public void setCommandArgsSet(Set<CommandArgs> commandArgsSet) {
        this.commandArgsSet = commandArgsSet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
