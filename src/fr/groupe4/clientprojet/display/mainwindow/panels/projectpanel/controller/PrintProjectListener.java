package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintProjectListener implements ActionListener, Printable {
    private JComponent componentToPrint;

    public PrintProjectListener(JComponent componentToPrint) {
        this.componentToPrint = componentToPrint;
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        /*
        System.out.println(componentToPrint.getWidth());
        System.out.println(componentToPrint.getHeight());

        System.out.println(pageFormat.getWidth());
        System.out.println(pageFormat.getHeight());

        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        */

        componentToPrint.printAll(g);

        return PAGE_EXISTS;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                /* The job did not successfully complete */
            }
        }
    }
}
