import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Main extends Frame {
    // The main window
    public static Main mainFrame;

    // The controller to store the data
    public static Controller ctrl = new Controller();

    // The list of items
    private List list;

    public static void main(String[] args) {
        new Main();
    }

    // The list of components for each type of answer
    private java.util.List<Component> comps1 = new ArrayList<>();
    private java.util.List<Component> comps2 = new ArrayList<>();
    private java.util.List<Component> comps3 = new ArrayList<>();

    public Main() {
        mainFrame = this;

        // Set the properties of the window
        this.setSize(400, 720);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add the elements for the question text
        Label label = new Label("Question: ");
        TextField fieldQuestion = new TextField();


        // The checkbox group for selecting the type of question
        CheckboxGroup group = new CheckboxGroup();
        Checkbox c1 = new Checkbox("True/False", group, true);
        Checkbox c2 = new Checkbox("Multiple", group, false);
        Checkbox c3 = new Checkbox("Fill", group, false);

        // Set handlers for changing the components
        c1.addItemListener(e -> {
            if (c1.getState()) {
                comps1.forEach((cmp) -> cmp.setVisible(true));
                comps2.forEach((cmp) -> cmp.setVisible(false));
                comps3.forEach((cmp) -> cmp.setVisible(false));
                mainFrame.setVisible(true);
            }
        });
        c2.addItemListener(e -> {
            if (c2.getState()) {
                comps1.forEach((cmp) -> cmp.setVisible(false));
                comps2.forEach((cmp) -> cmp.setVisible(true));
                comps3.forEach((cmp) -> cmp.setVisible(false));
                mainFrame.setVisible(true);
            }
        });
        c3.addItemListener(e -> {
            if (c3.getState()) {
                comps1.forEach((cmp) -> cmp.setVisible(false));
                comps2.forEach((cmp) -> cmp.setVisible(false));
                comps3.forEach((cmp) -> cmp.setVisible(true));
                mainFrame.setVisible(true);
            }
        });

        // Create the answer part of the frame
        Label labelAnswer = new Label("Answers: ");

        // For the true/false answer
        Checkbox tf = new Checkbox("True", false);
        comps1.add(tf);

        // For the multiple answers
        Checkbox mc1 = new Checkbox("True", true);
        TextField mfield1 = new TextField();
        Checkbox mc2 = new Checkbox("True", false);
        TextField mfield2 = new TextField();
        Checkbox mc3 = new Checkbox("True", false);
        TextField mfield3 = new TextField();

        comps2.add(mc1);
        comps2.add(mfield1);
        comps2.add(mc2);
        comps2.add(mfield2);
        comps2.add(mc3);
        comps2.add(mfield3);

        // For the string answer
        TextField ffield1 = new TextField();
        comps3.add(ffield1);

        // Create the add button
        Button buttonAdd = new Button("Add");

        // Create a handler for it
        buttonAdd.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Check if the text is set and show a message if it isn't
                String question = fieldQuestion.getText();
                if (question.equals("")) {
                    JOptionPane.showMessageDialog(mainFrame, "No question message", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add the answer
                Answer a = null;
                if (c1.getState()) {
                    // If it's a boolean answer
                    a = new BooleanAnswer(tf.getState());
                } else if (c2.getState()) {
                    // If it's multiple answer
                    String t1 = mfield1.getText();
                    String t2 = mfield2.getText();
                    String t3 = mfield3.getText();
                    if (t1.equals("") || t2.equals("") || t3.equals("")) {
                        JOptionPane.showMessageDialog(mainFrame, "Please insert text for every answer", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    a = new MultipleAnswer(t1, t2, t3, mc1.getState(), mc2.getState(), mc3.getState());
                } else if (c3.getState()) {
                    // If it's string answer
                    if (ffield1.getText().equals("")) {
                        JOptionPane.showMessageDialog(mainFrame, "Please insert text for the answer", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    a = new FillAnswer(ffield1.getText());
                }

                // Add the question to the controller
                Question q = new Question(Question.getNextID(), question, a);
                ctrl.questions.add(q);

                // And update the visual list
                updateList();
            }

            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });

        // Create a remove button
        Button buttonRemove = new Button("Remove");

        // Add a handler for it
        buttonRemove.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // If there's no item selected show message
                if (list.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(mainFrame, "No selected question", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get the ID of the item
                int id = Integer.parseInt(list.getSelectedItem().split("\\)")[0]);

                // Remove it from the controller
                ctrl.questions.removeIf(q -> q.id == id);

                // And update the list
                updateList();
            }

            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });

        // Create a modify button
        Button buttonModify = new Button("Modify");

        // Add a handler for it
        buttonModify.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // If no item is selected show a message
                if (list.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(mainFrame, "No selected question", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get the ID
                int id = Integer.parseInt(list.getSelectedItem().split("\\)")[0]);
                // Get the question
                Question question = ctrl.questions.stream().filter((q) -> q.id == id).findFirst().get();

                // Get the question's text
                String questionText = fieldQuestion.getText();

                // Show message if empty
                if (questionText.equals("")) {
                    JOptionPane.showMessageDialog(mainFrame, "No question message", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get the answer
                Answer a = null;
                if (c1.getState()) {
                    // If it's a boolean answer
                    a = new BooleanAnswer(tf.getState());
                } else if (c2.getState()) {
                    // If it's a multiple answer
                    String t1 = mfield1.getText();
                    String t2 = mfield2.getText();
                    String t3 = mfield3.getText();
                    if (t1.equals("") || t2.equals("") || t3.equals("")) {
                        JOptionPane.showMessageDialog(mainFrame, "Please insert text for every answer", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    a = new MultipleAnswer(t1, t2, t3, mc1.getState(), mc2.getState(), mc3.getState());
                } else if (c3.getState()) {
                    // If it's a string answer
                    if (ffield1.getText().equals("")) {
                        JOptionPane.showMessageDialog(mainFrame, "Please insert text for the answer", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    a = new FillAnswer(ffield1.getText());
                }

                // Update the question
                question.setAnswer(a);
                question.setText(questionText);

                // And the visual list
                updateList();
            }

            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });

        // Create a button for generating
        Button buttonGenerate = new Button("Generate test");

        // Add a handler
        buttonGenerate.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Create a new window
                Frame frame = new Frame("New test");
                frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
                frame.setSize(400, 400);

                // Create elements for it
                TextField fieldNumber = new TextField();
                TextArea test = new TextArea(10, 5);
                test.setEnabled(false);

                // Create a button for generating the test
                Button buttonGenerateTest = new Button("Generate");

                // Add a handler
                buttonGenerateTest.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Check if the number is specified and if that number is between 1 and size
                        if (fieldNumber.getText().equals("")) {
                            JOptionPane.showMessageDialog(mainFrame, "Please insert the number of questions you want", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        int num = Integer.parseInt(fieldNumber.getText());
                        if (num <= 0 || num > ctrl.questions.size()) {
                            JOptionPane.showMessageDialog(mainFrame, "Please insert a number between 1 and " + ctrl.questions.size(), "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // Shuffle the list
                        java.util.List<Question> list = new ArrayList<>(ctrl.questions);
                        Collections.shuffle(list);
                        list = list.subList(0, num);

                        // Create the string that represents the test
                        StringBuilder text = new StringBuilder();
                        for (Question q : list) {
                            text.append(q.getText()).append("\n");
                            if (q.getAnswer() instanceof BooleanAnswer) {
                                text.append("[ ] True / False\n");
                            } else if (q.getAnswer() instanceof FillAnswer) {
                                text.append("Answer here: \n");
                            } else if (q.getAnswer() instanceof MultipleAnswer) {
                                text.append("Multiple choice: \n");
                                text.append("[ ] ").append(((MultipleAnswer) q.getAnswer()).getAnswerText1()).append("\n")
                                    .append("[ ] ").append(((MultipleAnswer) q.getAnswer()).getAnswerText2()).append("\n")
                                    .append("[ ] ").append(((MultipleAnswer) q.getAnswer()).getAnswerText3()).append("\n");
                            }
                        }

                        // Set it to this field
                        test.setText(text.toString());
                    }
                    public void mousePressed(MouseEvent e) { }
                    public void mouseReleased(MouseEvent e) { }
                    public void mouseEntered(MouseEvent e) { }
                    public void mouseExited(MouseEvent e) { }
                });

                // Create the button for saving
                Button buttonSave = new Button("Save");

                // Add a handler
                buttonSave.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            // Open the file
                            BufferedWriter writer = new BufferedWriter(new FileWriter("questions.txt"));

                            // And write the text
                            writer.write(test.getText());

                            // Close the writer
                            writer.close();

                            // And the window
                            frame.setVisible(false);
                        } catch (IOException e1) {
                            throw new RuntimeException(e1);
                        }
                    }
                    public void mousePressed(MouseEvent e) { }
                    public void mouseReleased(MouseEvent e) { }
                    public void mouseEntered(MouseEvent e) { }
                    public void mouseExited(MouseEvent e) { }
                });

                frame.addWindowListener(new WindowListener() {
                    public void windowOpened(WindowEvent e) { }
                    public void windowClosing(WindowEvent e) { frame.setVisible(false); }
                    public void windowClosed(WindowEvent e) { }
                    public void windowIconified(WindowEvent e) { }
                    public void windowDeiconified(WindowEvent e) { }
                    public void windowActivated(WindowEvent e) { }
                    public void windowDeactivated(WindowEvent e) { }
                });

                // Add or these components to the frame in this order
                frame.add(fieldNumber);
                frame.add(test);
                frame.add(buttonGenerateTest);
                frame.add(buttonSave);
                frame.setVisible(true);
            }

            public void mousePressed(MouseEvent e) { }
            public void mouseReleased(MouseEvent e) { }
            public void mouseEntered(MouseEvent e) { }
            public void mouseExited(MouseEvent e) { }
        });

        // Create the list
        list = new List(20);

        // Add a handler for selection
        list.addItemListener(e -> {
            // If no item selected, nothing to do here
            if (list.getSelectedItem() == null) {
                return;
            }

            // Get the id
            int id = Integer.parseInt(list.getSelectedItem().split("\\)")[0]);
            // And the question
            Question q = ctrl.questions.stream().filter((qu) -> qu.id == id).findFirst().get();

            // And set all of its fields AND update the window's components
            fieldQuestion.setText(q.getText());
            if (q.getAnswer() instanceof BooleanAnswer) {
                c1.setState(true);
                comps1.forEach((cmp) -> cmp.setVisible(true));
                comps2.forEach((cmp) -> cmp.setVisible(false));
                comps3.forEach((cmp) -> cmp.setVisible(false));
                mainFrame.setVisible(true);
                tf.setState(((BooleanAnswer) q.getAnswer()).isResult());
            } else if (q.getAnswer() instanceof MultipleAnswer) {
                c2.setState(true);
                comps1.forEach((cmp) -> cmp.setVisible(false));
                comps2.forEach((cmp) -> cmp.setVisible(true));
                comps3.forEach((cmp) -> cmp.setVisible(false));
                mainFrame.setVisible(true);
                mfield1.setText(((MultipleAnswer) q.getAnswer()).getAnswerText1());
                mfield2.setText(((MultipleAnswer) q.getAnswer()).getAnswerText2());
                mfield3.setText(((MultipleAnswer) q.getAnswer()).getAnswerText3());
                mc1.setState(((MultipleAnswer) q.getAnswer()).isAnswerTrue1());
                mc2.setState(((MultipleAnswer) q.getAnswer()).isAnswerTrue2());
                mc3.setState(((MultipleAnswer) q.getAnswer()).isAnswerTrue3());
            } else if (q.getAnswer() instanceof FillAnswer) {
                c3.setState(true);
                comps1.forEach((cmp) -> cmp.setVisible(false));
                comps2.forEach((cmp) -> cmp.setVisible(false));
                comps3.forEach((cmp) -> cmp.setVisible(true));
                mainFrame.setVisible(true);
                ffield1.setText(((FillAnswer) q.getAnswer()).getText());
            }
        });

        // Add all the elements we created in this order
        this.add(label);
        this.add(fieldQuestion);
        this.add(c1);
        this.add(c2);
        this.add(c3);
        this.add(labelAnswer);
        this.add(tf);
        this.add(mc1);
        this.add(mfield1);
        this.add(mc2);
        this.add(mfield2);
        this.add(mc3);
        this.add(mfield3);
        this.add(ffield1);
        this.add(list);
        this.add(buttonAdd);
        this.add(buttonRemove);
        this.add(buttonModify);
        this.add(buttonGenerate);

        // Set a default state
        comps1.forEach((cmp) -> cmp.setVisible(true));
        comps2.forEach((cmp) -> cmp.setVisible(false));
        comps3.forEach((cmp) -> cmp.setVisible(false));

        // Set the window to be visible
        this.setVisible(true);

        // For closing the window
        this.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) { }
            public void windowClosing(WindowEvent e) { System.exit(0); }
            public void windowClosed(WindowEvent e) { }
            public void windowIconified(WindowEvent e) { }
            public void windowDeiconified(WindowEvent e) { }
            public void windowActivated(WindowEvent e) { }
            public void windowDeactivated(WindowEvent e) { }
        });
    }

    // Updates the list component
    public void updateList() {
        list.removeAll();
        for (Question q : ctrl.questions) {
            list.add(q.toString());
        }
    }
}
