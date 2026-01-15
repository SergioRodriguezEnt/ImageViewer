package software.ulpgc.ImageViewer.architecture.control;

public record PreviousCommand(ImageManager manager) implements Command {
    @Override
    public void execute() {
        manager.showPrevious();
    }
}
