#ifndef FENPRINCIPALE_H
#define FENPRINCIPALE_H

#include <QApplication>
#include <QWidget>
#include <QMainWindow> // La classe QMainWindow permet de créer une fenetre avec une barre de menu, une zone central, et une barre d'états (celle de dessous)
#include <QLabel>
#include <QBoxLayout>
#include <QMenuBar>
#include <QAction>

class FenPrincipale : public QMainWindow
{
public:
  FenPrincipale();

  void barreDeMenu();
  void zoneCentrale();

private:

};

#endif // FENPRINCIPALE_H
