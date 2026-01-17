package software.ulpgc.imageviewer.application;

import software.ulpgc.imageviewer.application.io.FileImageStore;
import software.ulpgc.imageviewer.application.ui.FileContainerSupplier;
import software.ulpgc.imageviewer.application.ui.Desktop;
import software.ulpgc.imageviewer.application.ui.SwingImageDisplay;
import software.ulpgc.imageviewer.architecture.control.ImageManager;
import software.ulpgc.imageviewer.architecture.control.NextCommand;
import software.ulpgc.imageviewer.architecture.control.PreviousCommand;
import software.ulpgc.imageviewer.architecture.control.SelectContainerCommand;
import software.ulpgc.imageviewer.architecture.io.ImageStore;

public class Main {
    static void main() {
        ImageStore store = new FileImageStore();
        FileContainerSupplier containerSupplier = new FileContainerSupplier();
        SwingImageDisplay display = new SwingImageDisplay();
        ImageManager manager = new ImageManager(display, store);
        manager.showCurrent();

        new Desktop(display)
                .add("next", new NextCommand(manager))
                .add("previous", new PreviousCommand(manager))
                .add("select folder", new SelectContainerCommand(store, manager, containerSupplier))
                .setVisible(true);
    }
}
