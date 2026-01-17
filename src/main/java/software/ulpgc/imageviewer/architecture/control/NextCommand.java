package software.ulpgc.imageviewer.architecture.control;

public record NextCommand(ImageManager manager) implements Command {
    @Override
    public void execute() {
        manager.showNext();
    }
}
