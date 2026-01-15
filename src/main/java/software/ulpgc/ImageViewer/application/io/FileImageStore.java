package software.ulpgc.ImageViewer.application.io;

import software.ulpgc.ImageViewer.architecture.io.ImageStore;
import software.ulpgc.ImageViewer.architecture.model.Image;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileImageStore implements ImageStore {
    private int current;
    private Map<Integer, Image> images;
    private String container;

    public FileImageStore() {
        setContainer("images");
    }

    @Override
    public Image previous() {
        current--;
        return current();
    }

    @Override
    public Image next() {
        current++;
        return current();
    }

    @Override
    public Image current() {
        normalizeIndex();
        if (images.containsKey(current)) return images.get(current);
        return readImage(new File(folderPath(), imageName()));
    }

    private void normalizeIndex() {
        int files = Objects.requireNonNull(folderPath().listFiles()).length;
        current = ((current % files) + files) % files;
    }

    private Image readImage(File file) {
        try (InputStream is = new ByteArrayInputStream(Files.readAllBytes(file.toPath()))) {
            Image image = new Image(file.getPath(), ImageIO.read(is));
            images.put(current, image);
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String imageName() {
        return Arrays.asList(Objects.requireNonNull(folderPath().list())).get(current);
    }

    private File folderPath() {
        return new File(container);
    }

    @Override
    public void setContainer(String container) {
        images = new HashMap<>();
        current = 0;
        this.container = container;
        current();
    }
}
