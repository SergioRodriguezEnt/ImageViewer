package software.ulpgc.ImageViewer.architecture.control;

import software.ulpgc.ImageViewer.architecture.io.ImageStore;

import java.util.function.Supplier;

public record SelectContainerCommand(ImageStore store,
                                     ImageManager manager,
                                     Supplier<String> containerSupplier) implements Command {
    @Override
    public void execute() {
        store.setContainer(containerSupplier.get());
        manager.showCurrent();
    }
}
