package software.ulpgc.ImageViewer.application.ui;

import software.ulpgc.ImageViewer.architecture.control.Command;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class Desktop extends JFrame {
    private final JButton previousButton;
    private final JButton nextButton;
    private final Map<String, Command> commands;

    public Desktop(SwingImageDisplay display) {
        this.setTitle("Image Viewer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);

        commands = new HashMap<>();

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(null);
        imagePanel.setPreferredSize(new Dimension(800, 600));

        previousButton = previousButton();
        imagePanel.add(previousButton);
        nextButton = nextButton();
        imagePanel.add(nextButton);

        display.setBounds(0, 0, 800, 600);
        imagePanel.add(display);

        imagePanel.addMouseListener(mouseListener(imagePanel));

        mainPanel.add(changeFolderButton(), BorderLayout.NORTH);
        mainPanel.add(imagePanel, BorderLayout.CENTER);

        this.add(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private JPanel changeFolderButton() {
        JPanel topBar = new JPanel(new FlowLayout());
        JButton button = new JButton("Change Folder");
        button.addActionListener(_ -> commands.get("select folder").execute());
        topBar.add(button);
        return topBar;
    }

    private JButton nextButton() {
        JButton button =  button("<", "next");
        button.setBounds(20, (600 - 100) / 2, 60, 100);
        return button;
    }

    private JButton previousButton() {
        JButton button = button(">", "previous");
        button.setBounds(800-80, (600 - 100) / 2, 60, 100);
        return button;
    }

    private JButton button(String text, String name) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setForeground(Color.white);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setVisible(true);
        button.setFocusable(false);
        button.addActionListener(_ -> commands.get(name).execute());
        return button;
    }

    private MouseListener mouseListener(JPanel panel) {
        return (new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                previousButton.setVisible(true);
                nextButton.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getX() < 0 || e.getX() >= panel.getWidth() || e.getY() < 0 || e.getY() >= panel.getHeight()) {
                    previousButton.setVisible(false);
                    nextButton.setVisible(false);
                }
            }
        });
    }

    public Desktop add(String name, Command command) {
        commands.put(name, command);
        return this;
    }
}
