package ru.lab.businessLogic.services.logicServices.commandServices;

import ru.lab.businessLogic.commands.AbstractCommand;
import ru.lab.businessLogic.commands.CommandType;

import java.util.List;
import java.util.Map;


/**
 * Сервис который обрабатывает команды и служит связующим звеном для паттерна *Команда*
 * @param <Result> - результат команды
 * @param <Query> - запрос к команде
 */
public interface CommandService<Result, Query> {
    List<AbstractCommand> getAllCommands();
    Map<CommandType, List<String>> getCommandMap();
    AbstractCommand addNewCommand(AbstractCommand command);
    List<AbstractCommand> addAllCommands(List<AbstractCommand> commands);
    Result execute(Query query);

}
