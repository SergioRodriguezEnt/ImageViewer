package software.ulpgc.imageviewer.architecture.control;

public record PreviousCommand(ImageManager manager) implements Command {
    @Override
    public void execute() {
        manager.showPrevious();
    }
}
