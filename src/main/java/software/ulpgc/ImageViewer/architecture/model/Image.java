package software.ulpgc.ImageViewer.architecture.model;

import java.awt.image.BufferedImage;

public record Image(String path, BufferedImage component) {
}
