import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DigitalNoticeBoard extends JFrame {
    private ArrayList<String> notices; // List to store notices
    private JTextArea noticeBoard;
    private JTextField inputField;
    private JLabel scrollingLabel;
    private Timer scrollTimer;
    private String scrollingText = "";

    public DigitalNoticeBoard() {
        // Initialize the notice list
        notices = new ArrayList<>();

        // Set up the main frame
        setTitle("Digital Notice Board");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Notice Board Area
        noticeBoard = new JTextArea();
        noticeBoard.setEditable(false);
        noticeBoard.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(noticeBoard), BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton addButton = new JButton("Add Notice");
        JButton deleteButton = new JButton("Delete Notice");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.WEST);
        inputPanel.add(deleteButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.NORTH);

        // Scrolling Notice Panel
        scrollingLabel = new JLabel(" ", JLabel.CENTER);
        scrollingLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        scrollingLabel.setForeground(Color.BLUE);
        add(scrollingLabel, BorderLayout.SOUTH);

        // Add Notice Action
        addButton.addActionListener(e -> {
            String notice = inputField.getText().trim();
            if (!notice.isEmpty()) {
                notices.add(notice);
                updateNoticeBoard();
                inputField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid notice.");
            }
        });

        // Delete Notice Action
        deleteButton.addActionListener(e -> {
            String noticeToDelete = inputField.getText().trim();
            if (!noticeToDelete.isEmpty()) {
                boolean found = false;
                for (int i = 0; i < notices.size(); i++) {
                    if (notices.get(i).equalsIgnoreCase(noticeToDelete)) {
                        notices.remove(i);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    updateNoticeBoard();
                    inputField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Notice not found. Please ensure the text matches exactly.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a notice to delete.");
            }
        });

        // Scrolling Timer
        scrollTimer = new Timer(150, new ActionListener() {
            private int index = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (scrollingText.length() > 0) {
                    scrollingLabel.setText(scrollingText.substring(index) + " " + scrollingText.substring(0, index));
                    index = (index + 1) % scrollingText.length();
                }
            }
        });
        scrollTimer.start();
    }

    // Update the notice board and scrolling text
    private void updateNoticeBoard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < notices.size(); i++) {
            sb.append((i + 1)).append(". ").append(notices.get(i)).append("\n");
        }
        noticeBoard.setText(sb.toString());
        updateScrollingText();
    }

    // Update scrolling text
    private void updateScrollingText() {
        if (notices.isEmpty()) {
            scrollingText = "No notices available.";
        } else {
            scrollingText = String.join(" | ", notices);
        }
    }

    // Main Method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DigitalNoticeBoard noticeBoard = new DigitalNoticeBoard();
            noticeBoard.setVisible(true);
        });
    }
}