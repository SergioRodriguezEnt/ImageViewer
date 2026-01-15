package software.ulpgc.ImageViewer.architecture.ui;

import software.ulpgc.ImageViewer.architecture.model.Image;

import java.util.List;
import java.util.function.IntConsumer;

public interface ImageDisplay {
    void show(List<Image> images, int... offsets);

    int width();

    void onShift(IntConsumer onShiftFn);

    void onRelease(IntConsumer onReleaseFn);
}
