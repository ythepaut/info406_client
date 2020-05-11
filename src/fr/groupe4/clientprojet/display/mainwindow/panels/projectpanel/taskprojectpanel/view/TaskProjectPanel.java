package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.taskprojectpanel.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.controller.NewTaskListener;
import fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.taskprojectpanel.controller.TaskProjectAddTimeSlot;
import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.task.Task;
import fr.groupe4.clientprojet.model.task.TaskList;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.*;

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
     *
     * @throws NullPointerException Si la liste des tâches est nulle
     */
    public TaskProjectPanel(Project project) throws NullPointerException {
        super(new BorderLayout());

        Communication comm = Communication.builder().startNow().getTaskList(project.getId()).build();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        String[] titles = {"TÂCHES", "DESCRIPTION", "DATE LIMITE", "AJOUT CRÉNEAUX"};

        c.gridy = 0;
        c.weighty = 0.0;

        for (int i=0; i<titles.length; i++) {
            c.gridx = i;

            c.weightx = 1.0;
            c.fill = GridBagConstraints.BOTH;

            panel.add(new JLabel(titles[i]), c);
        }

        comm.sleepUntilFinished();

        TaskList tasks = (TaskList) comm.getResult();

        if (tasks == null) {
            Logger.error("Tâches nulles");
            throw new NullPointerException("Tâches nulles");
        }
        else {
            for (int i=0; i<tasks.size(); i++) {
                Task task = tasks.get(i);

                c.gridy = i+1;
                c.weighty = ((float) (i+1)) / ((float) (tasks.size() + 1));
                c.weightx = 1.0;
                c.fill = GridBagConstraints.BOTH;

                c.gridx = 0;
                panel.add(new JLabel(task.getName()), c);

                String description = task.getDescription();

                if (description.isBlank()) {
                    description = " ";
                }

                c.gridx = 1;
                panel.add(new JLabel(description), c);

                LocalDateTime deadline = task.getDeadline();
                String deadlineString = " ";

                if (deadline != null) {
                    deadlineString = deadline.toString();
                }

                c.gridx = 2;
                panel.add(new JLabel(deadlineString), c);

                JButton b = new JButton("Ajouter un créneau");
                b.addActionListener(new TaskProjectAddTimeSlot(task));

                c.gridx = 3;

                if (i == tasks.size()-1 && tasks.size() >= MIN_NB_TASKS) {
                    c.anchor = GridBagConstraints.PAGE_END;
                }

                panel.add(b, c);
            }

            for (int i=tasks.size(); i<MIN_NB_TASKS; i++) {
                c.gridy = i+1;
                c.weighty = ((float) (i+1)) / ((float) (tasks.size() + 1));
                c.weightx = 1.0;
                c.fill = GridBagConstraints.BOTH;

                for (int x=0; x<4; x++) {
                    c.gridx = x;
                    panel.add(new JLabel(" "), c);
                }

                if (i == tasks.size()-1) {
                    c.anchor = GridBagConstraints.PAGE_END;
                }
            }

            JButton b = new JButton("Nouvelle tâche");
            b.addActionListener(new NewTaskListener(project));
            panel.add(b);

            add(panel, BorderLayout.CENTER);
            add(b, BorderLayout.SOUTH);
        }
    }
}
