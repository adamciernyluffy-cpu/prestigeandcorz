import javax.swing.*;
import java.awt.*;
import java.io.File;

public final class OrcaLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(OrcaLauncher::showWindow);
    }

    private static void showWindow() {
        JFrame frame = new JFrame("Orca Client Launcher");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(520, 360);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));
        root.setBackground(new Color(17, 22, 32));

        JLabel title = new JLabel("ORCA CLIENT");
        title.setForeground(new Color(183, 231, 255));
        title.setFont(new Font("SansSerif", Font.BOLD, 26));

        JLabel subtitle = new JLabel("<html>Minecraft Java 1.21.11 · Fabric<br>Client tools and module configuration</html>");
        subtitle.setForeground(new Color(220, 228, 238));

        JButton openOfficial = new JButton("Open Minecraft Launcher");
        openOfficial.addActionListener(e -> {
            try {
                if (Desktop.isDesktopSupported()) Desktop.getDesktop().browse(
                        java.net.URI.create("minecraft://"));
                else JOptionPane.showMessageDialog(frame, "Open Minecraft Launcher manually.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame,
                        "Could not open the launcher. Please open the official Minecraft Launcher manually.");
            }
        });

        JButton openFolder = new JButton("Open project folder");
        openFolder.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File(".").getAbsoluteFile());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, new File(".").getAbsolutePath());
            }
        });

        JPanel buttons = new JPanel(new GridLayout(2, 1, 0, 10));
        buttons.setOpaque(false);
        buttons.add(openOfficial);
        buttons.add(openFolder);

        JLabel note = new JLabel("<html><small>This launcher UI does not handle Microsoft login or download game files.<br>Use the official launcher for account authentication.</small></html>");
        note.setForeground(new Color(155, 170, 188));

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);
        center.add(subtitle, BorderLayout.NORTH);
        center.add(buttons, BorderLayout.CENTER);
        center.add(note, BorderLayout.SOUTH);

        root.add(title, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
        frame.setContentPane(root);
        frame.setVisible(true);
    }
}
