package software.ulpgc.imageviewer.architecture.control;

import software.ulpgc.imageviewer.architecture.io.ImageStore;

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
