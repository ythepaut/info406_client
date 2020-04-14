package fr.groupe4.clientprojet.display.dialog.taskcreationdialog.view;

import fr.groupe4.clientprojet.display.dialog.taskcreationdialog.controller.EventExitCreationDialog;
import fr.groupe4.clientprojet.display.dialog.taskcreationdialog.controller.EventTaskCreation;
import fr.groupe4.clientprojet.display.view.draw.DrawDialog;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Properties;

/**
 * Dialog de création de tâche
 */
public class TaskCreationDialog extends DrawDialog {
    /**
     * Parent
     */
    @Nullable
    private JFrame parent;

    /**
     * Id du projet
     */
    private long projectId;

    /**
     * Constructeur
     *
     * @param parent Parent
     * @param projectId Id du projet
     */
    public TaskCreationDialog(@Nullable JFrame parent, long projectId) {
        this.parent = parent;
        this.projectId = projectId;

        setTitle("Fenêtre de création de tâche");
        setModal(true);

        drawContent();
        setVisible(true);
    }

    /**
     * Affichage
     */
    @Override
    protected void drawContent() {
        setSize(300, 550);
        setResizable(false);
        setUndecorated(true);
        rootPane.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);

        GridBagConstraints c = new GridBagConstraints();

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
        JLabel labelnom = new JLabel("Entrez le nom de la tâche : ");
        add(labelnom,c);
        JTextField nomprojet = new JTextField(15);
        c.gridy++;
        add(nomprojet,c);
        String strnomprojet = nomprojet.getText();

        c.gridy++;
        JLabel labeldescription = new JLabel("Entrez la description de la tâche : ");
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
        JLabel datefinlabel = new JLabel("Entrez la date limite de la tâche : ");
        add(datefinlabel,c);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

        String datePattern = "d/MM/yyyy";
        SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");


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
        JButton creeprojet = new JButton("Création Tâche");
        creeprojet.addActionListener(new EventTaskCreation(this, model, nomprojet, description, projectId));
        add(creeprojet,c);
        c.gridx++;
        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new EventExitCreationDialog(this));
        add(cancelButton,c);
    }
}
