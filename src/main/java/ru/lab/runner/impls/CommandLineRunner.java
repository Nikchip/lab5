package ru.lab.runner.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import ru.lab.businessLogic.commands.AbstractCommand;
import ru.lab.businessLogic.commands.CommandArgs;
import ru.lab.businessLogic.commands.impls.*;
import ru.lab.businessLogic.domain.interaction.commandResultData.CommandResult;
import ru.lab.businessLogic.domain.interaction.query.Query;
import ru.lab.businessLogic.domain.movies.Movie;
import ru.lab.businessLogic.services.factories.movieFactories.MovieFactory;
import ru.lab.businessLogic.services.factories.movieFactories.impls.CommandLineMovieFactory;
import ru.lab.businessLogic.services.factories.queryFactories.QueryFactory;
import ru.lab.businessLogic.services.factories.queryFactories.impls.QueryFactoryImpl;
import ru.lab.businessLogic.services.logicServices.commandServices.CommandService;
import ru.lab.businessLogic.services.logicServices.commandServices.impl.CommandServiceImpl;
import ru.lab.businessLogic.services.logicServices.fileServices.FileService;
import ru.lab.businessLogic.services.logicServices.fileServices.exceptions.CorruptedFileException;
import ru.lab.businessLogic.services.logicServices.fileServices.exceptions.FileNotFoundException;
import ru.lab.businessLogic.services.logicServices.fileServices.exceptions.PermissionDeniedException;
import ru.lab.businessLogic.services.logicServices.fileServices.impls.MovieFileService;
import ru.lab.businessLogic.services.logicServices.queryService.QueryService;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.CommandDoesNotExistException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.MissedArgumentException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.NoSuchCommandTypeException;
import ru.lab.businessLogic.services.logicServices.queryService.exceptions.UnexpectedArgumentException;
import ru.lab.businessLogic.services.logicServices.queryService.impls.CommandLineQueryService;
import ru.lab.businessLogic.storage.CollectionWrapper;
import ru.lab.businessLogic.storage.impls.LinkedListWrapper;
import ru.lab.businessLogic.utils.scriptUtils.exceptions.InternalScriptException;
import ru.lab.businessLogic.utils.scriptUtils.exceptions.RecursionException;
import ru.lab.runner.ApplicationRunner;

import java.io.IOException;
import java.util.*;

public class CommandLineRunner implements ApplicationRunner {

    private ObjectMapper xmlMapper;
    private CollectionWrapper<Movie> collectionWrapper;
    private FileService<Movie> fileService;

    @Override
    public void start(List<String> args) {
        if(args.isEmpty()){
            System.out.println("pls, write path to save file and restart program!");
            return;
        }
        xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JSR310Module());
        collectionWrapper = new LinkedListWrapper();
        processFileService(args.get(0), xmlMapper);
        QueryFactory queryFactory = new QueryFactoryImpl();
        MovieFactory movieFactory = new CommandLineMovieFactory();
        CommandService<CommandResult, Query> commandService = new CommandServiceImpl();
        commandService.addAllCommands(testCommands(collectionWrapper, fileService, xmlMapper, commandService));
        QueryService queryService = new CommandLineQueryService(commandService.getCommandMap(), movieFactory, queryFactory, xmlMapper);
        System.out.println("Welcome! Use command 'help' to see information about commands");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("> ");
                String next = scanner.nextLine();
                if (next.equals("")) {
                    continue;
                }
                if (next.equals("exit")) {
                    System.out.println("Good bye!");
                    System.exit(0);
                }
                System.out.println(commandService.execute(queryService.createQuery(next)).getResultInfo());
            } catch (CommandDoesNotExistException e) {
                System.out.print("Command not found\n");
            } catch (JsonProcessingException e) {
                System.out.println("Cannot create serialize object\n");
            } catch (NoSuchCommandTypeException e) {
                System.out.println("Wrong command type exception\n");
            } catch (UnexpectedArgumentException e) {
                System.out.println("This command don't have any arguments");
            } catch (MissedArgumentException e) {
                System.out.println("This command must have argument");
            } catch (RecursionException e) {
                System.out.println("found recursion in script");
            } catch (InternalScriptException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("unknown exception. Read logs");
            }
        }
    }


    private void processFileService(String filePath, ObjectMapper objectMapper) {
        try {
            fileService = new MovieFileService(filePath, objectMapper);
            collectionWrapper.addAll(fileService.readFromFile());
        } catch (PermissionDeniedException e) {
            System.out.println("cannot open file! check permissions");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("file not found! write correct path");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("unknow file exception, read logs for more information");
            System.exit(0);
        } catch (CorruptedFileException e) {
            System.out.println("File corrupted! Check file data");
            System.exit(0);
        }
    }


        //todo replace to command factory
        private List<AbstractCommand> testCommands (CollectionWrapper < Movie > collectionWrapper,
                FileService < Movie > fileService,
                ObjectMapper objectMapper, CommandService commandService){
            List<AbstractCommand> commands = new ArrayList<>();
            Set<CommandArgs> objectArgs = Collections.singleton(CommandArgs.OBJECT_ARGUMENT);
            Set<CommandArgs> simpleArgs = Collections.singleton(CommandArgs.SIMPLE_ARGUMENT);
            Set<CommandArgs> mixedArgs = new HashSet<>();
            mixedArgs.addAll(Arrays.asList(CommandArgs.OBJECT_ARGUMENT, CommandArgs.SIMPLE_ARGUMENT));
            commands.add(new AddCommand(objectArgs, collectionWrapper, objectMapper));
            commands.add(new AddIfMinCommand(objectArgs, collectionWrapper, objectMapper));
            commands.add(new ClearCommand(null, collectionWrapper));
            commands.add(new InfoCommand(null, collectionWrapper));
            commands.add(new InsertCommand(mixedArgs, collectionWrapper, objectMapper));
            commands.add(new PrintDescendingCommand(null, collectionWrapper));
            commands.add(new RemoveAllByCommand(simpleArgs, collectionWrapper));
            commands.add(new RemoveCommand(simpleArgs, collectionWrapper));
            commands.add(new RemoveGreaterCommand(objectArgs, collectionWrapper, objectMapper));
            commands.add(new SaveCommand(null, collectionWrapper, fileService));
            commands.add(new ShowCommand(null, collectionWrapper));
            commands.add(new UpdateCommand(mixedArgs, collectionWrapper, objectMapper));
            commands.add(new ExecuteScriptCommand(simpleArgs, xmlMapper, commandService));
            commands.add(new HelpCommand(null, commands));
            return commands;
        }
    }
