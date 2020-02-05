package fr.groupe4.clientprojet.mainwindow.panels.leftpanel;

import fr.groupe4.clientprojet.mainwindow.panels.centerpanel.CenterPanel;
import fr.groupe4.clientprojet.utils.RoundButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class EventLeftPanel implements ActionListener, AdjustmentListener {
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

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        source.setDebutListe(e.getValue());
        source.setScrollValue(e.getValue());
        source.redraw();
    }
}
