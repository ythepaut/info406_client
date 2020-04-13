package fr.groupe4.clientprojet.display.dialog.projectcreationdialog.view;

import fr.groupe4.clientprojet.display.dialog.projectcreationdialog.controller.EventExitCreationDialog;
import fr.groupe4.clientprojet.display.dialog.projectcreationdialog.controller.EventProjectCreation;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import fr.groupe4.clientprojet.logger.Logger;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Fenetre de création de projet
 */
public class ProjectCreationDialog extends DrawDialog {

    //Les attributs du projet
    public String strnomprojet;
    public String strdescription;
    public String strdate;
    public Temporal localDate;

    //Ce qu'il faut pour bien configurer la date
    private String datePattern = "d/MM/yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    GridBagConstraints c = new GridBagConstraints();
    /**
     * La frame qui appelle ce dialog
     */
    private JFrame owner;

    /**
     * Constructeur
     * @param owner
     */
    public ProjectCreationDialog(JFrame owner) {
        setTitle("Fenêtre de création de Projet");
        this.owner = owner;
        setModal(true);

        drawContent();
        setVisible(true);
    }



    /**
     * Dessine le contenu du dialog de création
     */
    @Override
    protected void drawContent() {

        setSize(300, 550);
        setResizable(false);
        setUndecorated(true);
        rootPane.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        /**
         * Déclaration du layout
         */
        this.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(5,0,5,0);


        /**
         * Entrée du nom et description de projet
         */
        JLabel labelnom = new JLabel("Entrez le nom du projet : ");
        add(labelnom,c);
        JTextField nomprojet = new JTextField(15);
        c.gridy++;
        add(nomprojet,c);
        String strnomprojet = nomprojet.getText();

        c.gridy++;
        JLabel labeldescription = new JLabel("Entrez la description du projet : ");
        add(labeldescription,c);
        c.gridy++;
        JTextArea description = new JTextArea(18, 22);
        description.setLineWrap(true);
        add(description,c);
        String strdescription = description.getText();

        /**
         * Entrée de la date limite du projet
         */
        c.gridy++;
        JLabel datefinlabel = new JLabel("Entrez la date limite du projet : ");
        add(datefinlabel,c);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        //Création d'un JFormattedTextField.AbstractFormatter
        JFormattedTextField.AbstractFormatter form = new JFormattedTextField.AbstractFormatter() {
            public Object stringToValue(String text) throws ParseException {
                return dateFormatter.parseObject(text);
            }

            public String valueToString(Object value) throws ParseException {
                if (value != null)
                {
                    Calendar cal = (Calendar) value;
                    return dateFormatter.format(cal.getTime());
                }
                return "";

            }
        };

        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel,form);
        c.gridy++;
        add(datePicker,c);

        /**
         * Création des boutons de confirmation/annulation
         */
        c.gridwidth = 1;
        c.gridy++;
        JButton creeprojet = new JButton("Création Projet");
        creeprojet.addActionListener(new EventProjectCreation(this, model, nomprojet, description));
        add(creeprojet,c);
        c.gridx++;
        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new EventExitCreationDialog(this));
        add(cancelButton,c);

    }
}
