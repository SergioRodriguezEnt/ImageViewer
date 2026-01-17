package software.ulpgc.imageviewer.application.io;

import software.ulpgc.imageviewer.architecture.io.ImageStore;
import software.ulpgc.imageviewer.architecture.model.Image;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.IntStream;

public class FileImageStore implements ImageStore {
    private int current;
    private List<Integer> fileIndexes;
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
        current = ((current % fileIndexes.size()) + fileIndexes.size()) % fileIndexes.size();
    }

    private Image readImage(File file) {
        try (InputStream is = new ByteArrayInputStream(Files.readAllBytes(file.toPath()))) {
            Image image = new Image(file.getPath(), ImageIO.read(is));
            if (image.component() == null) System.out.println("Image not found? " + current);
            if (image.component() != null) images.put(current, image);
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String imageName() {
        return Arrays.asList(Objects.requireNonNull(folderPath().list())).get(fileIndexes.get(current));
    }

    private File folderPath() {
        return new File(container);
    }

    @Override
    public void setContainer(String container) {
        this.container = container;
        images = new HashMap<>();
        populateFileIndexes();
        current = 0;
        current();
    }

    private void populateFileIndexes() {
        List<File> files = Arrays.asList(Objects.requireNonNull(folderPath().listFiles()));
        fileIndexes = IntStream.range(0, files.size()).filter(i -> isFileValidImage(files.get(i))).boxed().toList();
    }

    private static final Set<String> validExtensions = Set.of("jpg", "jpeg", "png");
    private boolean isFileValidImage(File file) {
        return validExtensions.contains(extensionOf(file));
    }

    private String extensionOf(File file) {
        return file.getName().substring(file.getName().lastIndexOf('.') + 1);
    }
}
