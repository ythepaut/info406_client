package fr.groupe4.clientprojet.display.dialog.projectcreationdialog.controller;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.communication.CommunicationBuilder;
import fr.groupe4.clientprojet.display.dialog.projectcreationdialog.view.ProjectCreationDialog;
import fr.groupe4.clientprojet.model.project.Project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventProjectCreation implements ActionListener {

    ProjectCreationDialog source;

    public EventProjectCreation(ProjectCreationDialog source) {
        this.source = source;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.print(source.strdate); //Test pour voir ce que renvoie strdate
        //Communication.builder().startNow().sleepUntilFinished().createProject(source.strnomprojet, source.strdescription, source.localDate);
    }
}
