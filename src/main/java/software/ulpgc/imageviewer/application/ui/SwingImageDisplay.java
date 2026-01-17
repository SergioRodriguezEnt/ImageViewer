package software.ulpgc.imageviewer.application.ui;

import software.ulpgc.imageviewer.architecture.model.Image;
import software.ulpgc.imageviewer.architecture.ui.ImageDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.function.IntConsumer;

public class SwingImageDisplay extends JPanel implements ImageDisplay {
    private IntConsumer shift;
    private IntConsumer release;
    private List<Image> images;
    private int[] offsets;

    public SwingImageDisplay() {
        MouseAdapter adapter = new MouseAdapter();
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    @Override
    public void show(List<Image> images, int... offsets) {
        this.images = images;
        this.offsets = offsets;
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.GRAY);
        g.fillRect(0,0, getWidth(),getHeight());

        if  (images == null) return;

        for (int i = 0; i < images.size(); i++) {
            Image image = images.get(i);
            int[] size = fitImage(image.component().getWidth(), image.component().getHeight());
            int x = (getWidth() - size[0])/2;
            int y = (getHeight() - size[1])/2;
            g.drawImage(image.component(), x+offsets[i], y, size[0], size[1], null);
        }
    }

    private int[] fitImage(int width, int height) {
        if (width <= getWidth() && height <= getHeight()) return new int[]{width, height};
        return ratio(width, height) > ratio(getWidth(), getHeight()) ?
                new int[] {getWidth(), height * getWidth() / width} :
                new int [] {width * getHeight() / height, getHeight()};
    }

    private double ratio(int width, int height) {
        return width / (double) height;
    }

    @Override
    public int width() {
        return getWidth();
    }

    @Override
    public void onShift(IntConsumer onShiftFn) {
        this.shift = onShiftFn;
    }

    @Override
    public void onRelease(IntConsumer onReleaseFn) {
        this.release = onReleaseFn;
    }

    private class MouseAdapter implements MouseListener, MouseMotionListener {
        private int x;

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            SwingImageDisplay.this.release.accept(e.getX() - x);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            SwingImageDisplay.this.shift.accept(e.getX() - x);
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }
}
