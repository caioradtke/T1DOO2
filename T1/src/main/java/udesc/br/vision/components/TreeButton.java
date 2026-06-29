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
    }

    private void setUpIcon() {
        arrowUp = new ImageIcon("src/main/resources/assets/arrow-up.png").getImage();
        arrowDown = new ImageIcon("src/main/resources/assets/arrow-down.png").getImage();

        arrowUp = arrowUp.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        arrowDown = arrowDown.getScaledInstance(24, 24, Image.SCALE_SMOOTH);

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
