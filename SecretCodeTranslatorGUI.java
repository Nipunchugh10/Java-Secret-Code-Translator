import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SecretCodeTranslatorGUI extends JFrame {
    private JTextField inputField;
    private JTextField outputField;
    private JButton encodeButton;
    private JButton decodeButton;

    public SecretCodeTranslatorGUI() {
        // Set up the JFrame
        setTitle("Secret Code Translator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400); // Set medium size
        setLayout(null);

        // Center the JFrame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        // Create input field
        inputField = new JTextField();
        inputField.setBounds(20, 20, 540, 50); // Adjusted size
        add(inputField);

        // Create output field
        outputField = new JTextField();
        outputField.setBounds(20, 90, 540, 50); // Adjusted size
        outputField.setEditable(false);
        add(outputField);

        // Create encode button
        encodeButton = new JButton("Encode");
        encodeButton.setBounds(20, 160, 260, 50); // Adjusted size
        add(encodeButton);

        // Create decode button
        decodeButton = new JButton("Decode");
        decodeButton.setBounds(280, 160, 260, 50); // Adjusted size
        add(decodeButton);

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                String[] words = input.split(" ");
                String encodedMessage = encodeMessage(words);
                outputField.setText(encodedMessage);
            }
        });

        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                String[] words = input.split(" ");
                String decodedMessage = decodeMessage(words, outputField);
                outputField.setText(decodedMessage);
            }
        });

        // Create open buttons
        JButton openEncodedButton = new JButton("Open Encoded Messages");
        openEncodedButton.setBounds(20, 230, 260, 50); // Adjusted size
        add(openEncodedButton);

        JButton openDecodedButton = new JButton("Open Decoded Messages");
        openDecodedButton.setBounds(300, 230, 260, 50); // Adjusted size
        add(openDecodedButton);

        // Add action listeners to open buttons
        openEncodedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMessagesFile("Java Encoded Message.txt");
            }
        });

        openDecodedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMessagesFile("Java Decoded Message.txt");
            }
        });
    }

    public static void saveMessageToFile(String filename, String message) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
            writer.write(message);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz1234567(){}[]`?<>890!@#$%^&*~_+=-";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }

    public static String encodeMessage(String[] words) {
        StringBuilder encodedMessage = new StringBuilder();

        for (String word : words) {
            if (word.length() >= 1) {
                String randomPrefix = generateRandomString(3);
                String randomSuffix = generateRandomString(3);
                String encodedWord = randomPrefix + word.substring(1) + word.charAt(0) + randomSuffix;
                encodedMessage.append(encodedWord).append(" ");
            } else {
                encodedMessage.append(new StringBuilder(word).reverse()).append(" ");
            }
        }

        return encodedMessage.toString().trim();
    }

    public static String decodeMessage(String[] words, JTextField outputField) {
        StringBuilder decodedMessage = new StringBuilder();

        for (String word : words) {
            if (word.length() >= 3) {
                String decodedWord = word.substring(3, word.length() - 3);
                decodedWord = decodedWord.charAt(decodedWord.length() - 1) + decodedWord.substring(0, decodedWord.length() - 1);
                decodedMessage.append(decodedWord).append(" ");
            } else {
                decodedMessage.append(new StringBuilder(word).reverse()).append(" ");
            }
        }

        String decodedMessageString = decodedMessage.toString().trim();
        outputField.setText(decodedMessageString);
        saveMessageToFile("Javaprojectdecodedmessages.txt", decodedMessageString);
        return decodedMessageString;
    }

    private void openMessagesFile(String filename) {
        try {
            Desktop.getDesktop().open(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SecretCodeTranslatorGUI gui = new SecretCodeTranslatorGUI();
                gui.setVisible(true);
            }
        });
    }
}
