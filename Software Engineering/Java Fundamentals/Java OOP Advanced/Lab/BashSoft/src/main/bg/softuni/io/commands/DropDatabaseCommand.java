package main.bg.softuni.io.commands;

import main.bg.softuni.annotations.Alias;
import main.bg.softuni.annotations.Inject;
import main.bg.softuni.contracts.Database;
import main.bg.softuni.exceptions.InvalidInputException;
import main.bg.softuni.io.OutputWriter;

@Alias("dropdb")
public class DropDatabaseCommand extends Command {

    @Inject
    private Database studentRepository;

    public DropDatabaseCommand(String input,String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if (data.length != 1) {
            throw new InvalidInputException(this.getInput());
        }

        this.studentRepository.unloadData();
        OutputWriter.writeMessageOnNewLine("Database dropped!");
    }
}
