package udesc.br.vision.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TreeButton extends JComponent {

    private List<JButton> buttons;
    private JButton btnTree;
    boolean isActive = false;
    private ImageIcon icon;
    private Image arrowUp;
    private Image arrowDown;

    public TreeButton(JButton btnTree) {
        this.buttons = new ArrayList<>();
        this.btnTree = btnTree;

        setUpIcon();
        this.btnTree.addActionListener(e -> aoClicar());
//        this.btnTree.setBorderPainted(false);
//        this.btnTree.setBackground(new Color(240, 240, 240));
    }

    private void setUpIcon() {
        arrowUp = new ImageIcon("src/assets/arrow-up.png").getImage();
        arrowDown = new ImageIcon("src/assets/arrow-down.png").getImage();

        arrowUp = arrowUp.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        arrowDown = arrowDown.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        icon = new ImageIcon(arrowDown);
        btnTree.setIcon(icon);
    }

    public void addButton(JButton button) {
        buttons.add(button);
        button.setVisible(false);
    }

    private void aoClicar() {
        setActive(!isActive);
    }

    public void setActive(boolean active) {
        isActive = active;
        icon.setImage(active ? arrowUp : arrowDown);

        for (JButton button : buttons) {
            button.setVisible(isActive);
        }
    }
}
