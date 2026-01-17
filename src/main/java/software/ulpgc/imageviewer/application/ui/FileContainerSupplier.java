package software.ulpgc.imageviewer.application.ui;

import javax.swing.*;
import java.io.File;
import java.util.function.Supplier;

public class FileContainerSupplier extends JFileChooser implements Supplier<String> {
    @Override
    public void approveSelection() {
        File selected = getSelectedFile();
        if (selected != null && selected.isDirectory()) {
            super.approveSelection();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a directory.",
                    "Invalid selection",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    @Override
    public String get() {
        setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        setDialogType(JFileChooser.OPEN_DIALOG);
        setAcceptAllFileFilterUsed(true);

        int result = showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return getSelectedFile().getAbsolutePath();
        }

        return "images";
    }
}
