package software.ulpgc.imageviewer.architecture.control;

import software.ulpgc.imageviewer.architecture.io.ImageStore;
import software.ulpgc.imageviewer.architecture.ui.ImageDisplay;

import java.util.List;

public class ImageManager {
    private final ImageDisplay display;
    private final ImageStore store;

    public ImageManager(ImageDisplay display, ImageStore store) {
        this.display = display;
        this.store = store;

        display.onShift(this::shiftImages);
        display.onRelease(this::switchImages);
    }

    private void switchImages(int offset) {
        if (Math.abs(offset) * 2 > display.width()) {
            if (offset < 0) store.next();
            else store.previous();
        }
        display.show(List.of(store.current()), 0);
    }

    private void shiftImages(int offset) {
        display.show(
                List.of(store.current(),
                        offset < 0 ? store.next() : store.previous()),
                offset,
                offset < 0 ? display.width() + offset : offset - display.width()
        );
        if (offset < 0) {
            store.previous();
        } else {
            store.next();
        }
    }

    public void showNext() {
        display.show(List.of(store.next()), 0);
    }

    public void showPrevious() {
        display.show(List.of(store.previous()), 0);
    }

    public void showCurrent() {
        display.show(List.of(store.current()), 0);
    }
}
