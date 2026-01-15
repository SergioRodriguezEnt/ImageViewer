package software.ulpgc.ImageViewer.architecture.io;

import software.ulpgc.ImageViewer.architecture.model.Image;

public interface ImageStore {
    Image previous();

    Image next();

    Image current();

    void setContainer(String container);
}
