package software.ulpgc.imageviewer.architecture.model;

import java.awt.image.BufferedImage;

public record Image(String path, BufferedImage component) {
}
