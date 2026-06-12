package udesc.br.vision;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TreeButton extends JButton {

    private List<JButton> buttons;

    public TreeButton() {
        buttons = new ArrayList<JButton>();
    }

    public void addButton(JButton button) {
        buttons.add(button);
        this.addActionListener(e -> aoClicar());
        button.setVisible(false);
    }

    private void aoClicar() {
        int spacing = 0;
        for (JButton button : buttons) {
            button.setAlignmentY(this.getAlignmentY() + spacing);
            button.setVisible(true);
            spacing += 15;
        }
    }
}
