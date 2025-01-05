package PaintPCK;

/**
 *
 * @author Ahmed  Abdelrahman
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Buttons extends JPanel {
    private int x1, x2, y1, y2;
    private Shape shape;
    public static Color current_color = Color.BLACK;
    private Color backgroundColor = Color.WHITE; // New background color variable
    private boolean _fill, _dott, _erase;
    String flag = "freehand";
    ArrayList<Shape> arr;
    private Stack<Shape> redoStack;

    // UI Components
    private JPanel toolbarPanel;
    private JPanel drawingPanel;
    private JButton colorButton, clr, undo, redo, save, backgroundColorButton; // Added background color button
    private JCheckBox dott, solid;
    private ButtonGroup shapes;
    private JRadioButton rec, lin, ov, pen, erase;

    // Shapes
    private Oval oval;
    private Rectangle rect;
    private Line line;
    private FreeHand freehand;
    private FreeHand eraser;

    public Buttons() {
        setLayout(new BorderLayout());

        // Initialize toolbar
        toolbarPanel = new JPanel();
        toolbarPanel.setBackground(new Color(240, 240, 240));
        toolbarPanel.setPreferredSize(new Dimension(getWidth(), 80));

        // Initialize drawing panel
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(backgroundColor); // Set the new background color
                Graphics2D g2d = (Graphics2D) g;

                // Draw all shapes in the array
                for (Shape value : arr) {
                    value.drawShape(g2d);
                }

                // Draw current shape if exists
                if (rect != null) rect.drawShape(g2d);
                if (line != null) line.drawShape(g2d);
                if (oval != null) oval.drawShape(g2d);
                if (freehand != null) freehand.drawShape(g2d);
                if (eraser != null) eraser.drawShape(g2d);
            }
        };
        drawingPanel.setBackground(Color.WHITE); // Default background

        // Initialize collections
        arr = new ArrayList<>();
        redoStack = new Stack<>();
        shape = null;

        // Setup UI components
        initializeToolbar();
        addDrawingPanelListeners();

        // Add panels to main container
        add(toolbarPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
    }

    private void initializeColorPalette() {
        JButton colorPickerButton = new JButton("Pick Color");
        colorPickerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color chosenColor = JColorChooser.showDialog(null, "Choose a Color", current_color);
                if (chosenColor != null) {
                    current_color = chosenColor;
                    repaint();
                }
            }
        });
        toolbarPanel.add(colorPickerButton);
    }

    private void initializeToolbar() {
        toolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Add color palette
        initializeColorPalette();

        // Background color button
        backgroundColorButton = new JButton("Background Color");
        backgroundColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color chosenColor = JColorChooser.showDialog(null, "Choose Background Color", backgroundColor);
                if (chosenColor != null) {
                    backgroundColor = chosenColor;
                    drawingPanel.setBackground(backgroundColor); // Update drawing panel background
                    repaint();
                }
            }
        });
        toolbarPanel.add(backgroundColorButton);

        // Clear button
        clr = createStyledButton("Clear All");
        clr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arr.clear();
                drawingPanel.repaint();
            }
        });

        // Undo button
        undo = createStyledButton("Undo");
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!arr.isEmpty()) {
                    redoStack.push(arr.remove(arr.size() - 1));
                    drawingPanel.repaint();
                }
            }
        });

        // Redo button
        redo = createStyledButton("Redo");
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!redoStack.isEmpty()) {
                    arr.add(redoStack.pop());
                    drawingPanel.repaint();
                }
            }
        });

        // Save button
        save = createStyledButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDrawing();
            }
        });

        // Shape selection buttons
        shapes = new ButtonGroup();

        rec = new JRadioButton("Rectangle");
        rec.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = "Rectangle";
            }
        });

        lin = new JRadioButton("Line");
        lin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = "Line";
            }
        });

        ov = new JRadioButton("Oval");
        ov.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = "Oval";
            }
        });

        pen = new JRadioButton("Pencil");
        pen.setSelected(true);
        pen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = "freehand";
                if (current_color == Color.WHITE) {
                    current_color = Color.BLACK;
                }
            }
        });

        erase = new JRadioButton("Eraser");
        erase.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                flag = "eraser";
                _erase = e.getStateChange() == ItemEvent.SELECTED;
            }
        });

        // Add all shape buttons to button group
        shapes.add(rec);
        shapes.add(lin);
        shapes.add(ov);
        shapes.add(pen);
        shapes.add(erase);

        // Style checkboxes
        dott = new JCheckBox("Fill");
        dott.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                _dott = e.getStateChange() == ItemEvent.SELECTED;
            }
        });

        solid = new JCheckBox("Dotted");
        solid.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                _fill = e.getStateChange() == ItemEvent.SELECTED;
            }
        });

        // Add all components to toolbar
        toolbarPanel.add(clr);
        toolbarPanel.add(undo);
        toolbarPanel.add(redo);
        toolbarPanel.add(save);
        toolbarPanel.add(rec);
        toolbarPanel.add(lin);
        toolbarPanel.add(ov);
        toolbarPanel.add(pen);
        toolbarPanel.add(erase);
        toolbarPanel.add(dott);
        toolbarPanel.add(solid);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 30));
        button.setBackground(new Color(220, 220, 220));
        button.setFocusPainted(false);
        return button;
    }

    private void addDrawingPanelListeners() {
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();

                switch (flag) {
                    case "Rectangle":
                        rect = new Rectangle(x1, y1, x1, y1, _fill, _dott, current_color, false);
                        break;
                    case "Line":
                        line = new Line(x1, y1, x1, y1, _fill, _dott, current_color, false);
                        break;
                    case "Oval":
                        oval = new Oval(x1, y1, x1, y1, _fill, _dott, current_color, false);
                        break;
                    case "freehand":
                        freehand = new FreeHand(x1, y1, x1, y1, _fill, _dott, current_color, false);
                        break;
                    case "eraser":
                        eraser = new FreeHand(x1, y1, x1, y1, false, false, Color.WHITE, true);
                        break;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();

                switch (flag) {
                    case "Rectangle":
                        rect.setX2(x2);
                        rect.setY2(y2);
                        arr.add(rect);
                        rect = null;
                        break;
                    case "Line":
                        line.setX2(x2);
                        line.setY2(y2);
                        arr.add(line);
                        line = null;
                        break;
                    case "Oval":
                        oval.setX2(x2);
                        oval.setY2(y2);
                        arr.add(oval);
                        oval = null;
                        break;
                    case "freehand":
                        freehand.setX2(x2);
                        freehand.setY2(y2);
                        arr.add(freehand);
                        freehand = null;
                        break;
                    case "eraser":
                        eraser.setX2(x2);
                        eraser.setY2(y2);
                        arr.add(eraser);
                        eraser = null;
                        break;
                }
                drawingPanel.repaint();
            }
        });

        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();

                switch (flag) {
                    case "Rectangle":
                        rect.setX2(x2);
                        rect.setY2(y2);
                        break;
                    case "Line":
                        line.setX2(x2);
                        line.setY2(y2);
                        break;
                    case "Oval":
                        oval.setX2(x2);
                        oval.setY2(y2);
                        break;
                    case "freehand":
                        freehand.setX2(x2);
                        freehand.setY2(y2);
                        break;
                    case "eraser":
                        eraser.setX2(x2);
                        eraser.setY2(y2);
                        break;
                }
                drawingPanel.repaint();
            }
        });
    }

    private void saveDrawing() {
        BufferedImage image = new BufferedImage(drawingPanel.getWidth(),
                (drawingPanel.getHeight() - 80),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        drawingPanel.paint(g2d);
        g2d.dispose();

        try {
            String filePath = "drawing.png";
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Image");
            fileChooser.setSelectedFile(new File(filePath));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                ImageIO.write(image, "png", fileToSave);
                System.out.println("Image saved successfully to: " + fileToSave.getAbsolutePath());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to save image.");
        }
    }
}