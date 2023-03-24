package isel.mpd.mvc.model.commands;

public interface Command {
    void execute();

    void undo();
}
