package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.task.Task;

/**
 * Adds a task to a person in the address book.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "addtask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to a student. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TASK_DESCRIPTION + "TASK DESCRIPTION "
            + PREFIX_TASK_DEADLINE + "TASK DEADLINE (YYYY-MM-DD)";

    public static final String MESSAGE_SUCCESS = "Added task: %1$s for Student %2$s by %3$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exist in the address book.";
    public static final String MESSAGE_DUPLICATE_TASK = "Task already exists in the student's task list!";

    private final Name name;
    private final Task taskToAdd;

    /**
     * Creates an AddTaskCommand to add the specified {@code Task} to a {@code Person}.
     */
    public AddTaskCommand(Name name, Task task) {
        requireNonNull(name);
        requireNonNull(task);
        this.name = name;
        this.taskToAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person targetPerson = model.getPersonByName(name);
        if (targetPerson == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        if (targetPerson.getTaskList().contains(taskToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        targetPerson.getTaskList().add(taskToAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                taskToAdd.getTaskDescription(), targetPerson.getName(), taskToAdd.getTaskDeadline()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTaskCommand)) {
            return false;
        }

        AddTaskCommand otherAddTaskCommand = (AddTaskCommand) other;
        return name.equals(otherAddTaskCommand.name)
                && taskToAdd.equals(otherAddTaskCommand.taskToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("taskToAdd", taskToAdd)
                .toString();
    }
}
