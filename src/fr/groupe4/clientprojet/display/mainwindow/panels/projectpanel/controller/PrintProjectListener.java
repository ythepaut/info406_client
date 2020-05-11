package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller;

import fr.groupe4.clientprojet.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * Permet d'imprimer un composant Swing
 */
public class PrintProjectListener implements ActionListener, Printable {
    /**
     * Composant à imprimer
     */
    private JComponent componentToPrint;

    /**
     * Constructeur
     *
     * @param componentToPrint Composant à imprimer
     */
    public PrintProjectListener(JComponent componentToPrint) {
        this.componentToPrint = componentToPrint;
    }

    /**
     * Imprime
     *
     * @param g          Espace pour imprimer
     * @param pageFormat Options d'impression
     * @param pageIndex  Index de la page à imprimer (ici 0 obligatoirement)
     * @return Si la page a été trouvée ou non
     */
    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            // Notre condition de sortie
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;

        double scale = Math.min(pageFormat.getHeight() / componentToPrint.getWidth(),
                pageFormat.getWidth() / componentToPrint.getHeight()
        );

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.scale(scale, scale);

        g2d.translate(pageFormat.getHeight(), 0);
        g2d.rotate(Math.PI / 2);

        componentToPrint.printAll(g2d);

        return PAGE_EXISTS;
    }

    /**
     * Bouton cliqué
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                Logger.warning("Erreur lors de l'impression : ", ex);
            }
        }
    }
}
