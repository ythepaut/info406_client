package fr.groupe4.clientprojet.mainwindow.panels.leftpanel;

import fr.groupe4.clientprojet.mainwindow.panels.centerpanel.CenterPanel;
import fr.groupe4.clientprojet.utils.RoundButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventLeftPanel implements ActionListener {
    private CenterPanel centerPanel;
    private LeftPanel source;

    public EventLeftPanel(LeftPanel source, CenterPanel centerPanel) {
        this.source = source;
        this.centerPanel = centerPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        centerPanel.setView(e.getActionCommand());

        for (RoundButton button : source.getButtons()) {
            if (button.getActionCommand().equals(centerPanel.getView())) button.setSelected(true);
            else button.setSelected(false);
        }
    }
}
