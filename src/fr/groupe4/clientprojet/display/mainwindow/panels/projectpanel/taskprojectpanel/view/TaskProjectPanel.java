package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.taskprojectpanel.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller.NewTaskListener;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.taskprojectpanel.controller.TaskProjectAddTimeSlot;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.parameters.Parameters;
import fr.groupe4.clientprojet.model.parameters.themes.Theme;
import fr.groupe4.clientprojet.model.parameters.themes.ThemeName;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.task.Task;
import fr.groupe4.clientprojet.model.task.TaskList;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;

/**
 * Panel des tâches
 */
public class TaskProjectPanel extends JPanel {
    private static final int MIN_NB_TASKS = 20;

    /**
     * Constructeur
     *
     * @param project Projet associé
     * @throws NullPointerException Si la liste des tâches est nulle
     */
    public TaskProjectPanel(Project project) throws NullPointerException {
        super(new BorderLayout());

        Communication comm = Communication.builder().startNow().getTaskList(project.getId()).build();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        panel.setBackground(Theme.FOND.getColor(Parameters.getThemeName()));
        String[] titles = {"TÂCHES", "DESCRIPTION", "DATE LIMITE", "AJOUT CRÉNEAUX"};

        c.gridy = 0;
        c.weighty = 0.0;

        for (int i = 0; i < titles.length; i++) {
            c.gridx = i;

            c.weightx = 1.0;
            c.fill = GridBagConstraints.BOTH;
            JLabel t = new JLabel(titles[i]);
            t.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
            panel.add(t, c);
        }

        comm.sleepUntilFinished();

        TaskList tasks = (TaskList) comm.getResult();

        if (tasks == null) {
            Logger.error("Tâches nulles");
            throw new NullPointerException("Tâches nulles");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);

                c.gridy = i + 1;
                c.weighty = ((float) (i + 1)) / ((float) (tasks.size() + 1));
                c.weightx = 1.0;
                c.fill = GridBagConstraints.BOTH;

                c.gridx = 0;
                JLabel n = new JLabel(task.getName());
                n.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
                panel.add(n, c);

                String description = task.getDescription();

                if (description.isBlank()) {
                    description = " ";
                }

                c.gridx = 1;
                JLabel d = new JLabel(description);
                d.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
                panel.add(d, c);

                LocalDateTime deadline = task.getDeadline();
                String deadlineString = " ";

                if (deadline != null) {
                    deadlineString = deadline.toString();
                }

                c.gridx = 2;
                JLabel dl = new JLabel(deadlineString);
                dl.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
                panel.add(dl, c);

                JButton b = new JButton("Ajouter un créneau");
                b.addActionListener(new TaskProjectAddTimeSlot(task));
                b.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
                b.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
                c.gridx = 3;

                if (i == tasks.size() - 1 && tasks.size() >= MIN_NB_TASKS) {
                    c.anchor = GridBagConstraints.PAGE_END;
                }

                panel.add(b, c);
            }

            for (int i = tasks.size(); i < MIN_NB_TASKS; i++) {
                c.gridy = i + 1;
                c.weighty = ((float) (i + 1)) / ((float) (tasks.size() + 1));
                c.weightx = 1.0;
                c.fill = GridBagConstraints.BOTH;

                for (int x = 0; x < 4; x++) {
                    c.gridx = x;
                    panel.add(new JLabel(" "), c);
                }

                if (i == tasks.size() - 1) {
                    c.anchor = GridBagConstraints.PAGE_END;
                }
            }

            JButton b = new JButton("Nouvelle tâche");
            b.setBackground(Theme.FOND_BUTTON.getColor(Parameters.getThemeName()));
            b.setForeground(Theme.POLICE_NORMAL.getColor(Parameters.getThemeName()));
            b.addActionListener(new NewTaskListener(project));
            panel.add(b);

            add(panel, BorderLayout.CENTER);
            add(b, BorderLayout.SOUTH);
        }
    }
}
