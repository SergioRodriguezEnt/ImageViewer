package software.ulpgc.ImageViewer.application;

import software.ulpgc.ImageViewer.application.io.FileImageStore;
import software.ulpgc.ImageViewer.application.ui.FileContainerSupplier;
import software.ulpgc.ImageViewer.application.ui.Desktop;
import software.ulpgc.ImageViewer.application.ui.SwingImageDisplay;
import software.ulpgc.ImageViewer.architecture.control.ImageManager;
import software.ulpgc.ImageViewer.architecture.control.NextCommand;
import software.ulpgc.ImageViewer.architecture.control.PreviousCommand;
import software.ulpgc.ImageViewer.architecture.control.SelectContainerCommand;
import software.ulpgc.ImageViewer.architecture.io.ImageStore;

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
