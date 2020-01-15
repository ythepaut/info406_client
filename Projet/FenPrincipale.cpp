#include "FenPrincipale.h"

FenPrincipale::FenPrincipale()
{
  setMinimumSize(600, 400);
  barreDeMenu();
  zoneCentrale();
}


void FenPrincipale::barreDeMenu()
{
  /*
   * Création de la barre de menu
   * On ajoute tout d'abord chaque menu
   * Qu'on rempli ensuite d'actions et de sous-menu (eux-même rempli d'actions)
   *
   * Les actions sont parfois des attributs de l'objet, car ils sont utilisé pour la barre de menu, et pour la barre d'outils
   * Ceci évite de les dupliquer, pour les actions comme quitter, cette précaution est inutile
   */

  QMenu *menuFichier = menuBar()->addMenu(tr("&Fichier")); // Création d'un menu
  // Le & sert à définir le raccourci, ici F (alt+F)
  // La fonction tr() sert pour la traduction
  QMenu *fichiersRecents = menuFichier->addMenu("Fichiers &récents"); // Ajout d'un sous-menu
  fichiersRecents->addAction("Fichier 1");
  fichiersRecents->addAction("Fichier 2");
  fichiersRecents->addAction("Fichier 3");

  QAction *actionQuitter = new QAction(tr("&Quitter", "Quitter le logiciel"), this); // Création de l'action quitter
  // <!> https://openclassrooms.com/fr/courses/1894236-programmez-avec-le-langage-c/1901168-configurez-la-fenetre-principale#/id/r-3346558 <!>
  actionQuitter->setShortcut(QKeySequence("Ctrl+Q")); // Définition du raccourcis pour quitter
  connect(actionQuitter, SIGNAL(triggered()), qApp, SLOT(quit())); // Ajout de l'event pour quitter
  menuFichier->addAction(actionQuitter); // Ajout de l'action quitter au menu fichier
}

void FenPrincipale::zoneCentrale()
{
  /*
   * Création du widget central de la fenetre
   * On créé généralement un widget simple, dans lequel on met un layout qui peut lui contenir plusieurs widgets et layout
   */

  QWidget *zoneCentrale = new QWidget;

  QLabel *label = new QLabel(tr("Ceci est la zone centrale", "Présentation de la zone centrale"));
  // On peut ajouter un second paramètre dans tr(), il sert de commentaire pour la traduction

  QHBoxLayout *layout = new QHBoxLayout; // Création d'un layout horizontal
  layout->addWidget(label); // Ajout du label dans le layout

  zoneCentrale->setLayout(layout); // Définition du layout du widget

  setCentralWidget(zoneCentrale); // Définition du widget central
}
