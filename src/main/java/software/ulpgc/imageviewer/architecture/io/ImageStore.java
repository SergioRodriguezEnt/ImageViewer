package software.ulpgc.imageviewer.architecture.io;

import software.ulpgc.imageviewer.architecture.model.Image;

public interface ImageStore {
    Image previous();

    Image next();

    Image current();

    void setContainer(String container);
}
