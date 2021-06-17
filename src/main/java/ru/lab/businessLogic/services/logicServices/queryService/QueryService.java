package ru.lab.businessLogic.services.logicServices.queryService;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.CommandDoesNotExistException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.MissedArgumentException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.NoSuchCommandTypeException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.UnexpectedArgumentException;
import ru.lab.businessLogic.utils.scriptUtils.exceptions.InternalScriptException;
import ru.lab.businessLogic.utils.scriptUtils.exceptions.RecursionException;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * централизированный сервис для генерации запросов
 */
public interface QueryService {
    Query createQuery(String commandData) throws CommandDoesNotExistException, JsonProcessingException, NoSuchCommandTypeException, UnexpectedArgumentException, MissedArgumentException, FileNotFoundException, InternalScriptException, RecursionException;
}
