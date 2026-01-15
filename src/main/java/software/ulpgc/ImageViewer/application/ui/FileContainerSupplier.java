package software.ulpgc.ImageViewer.application.ui;

import javax.swing.*;
import java.util.function.Supplier;

public class FileContainerSupplier implements Supplier<String> {
    @Override
    public String get() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile().getAbsolutePath();
    }
}
