Projet Cercle
=============

L'application <b>Cercle</b> est un gestionnaire de projet multi plateforme complet comprenant différents moyens de communications (vocal, écrit), un gestionnaire de fichiers sous forme d'arborescence avec différents droits au niveau des cercles, le tout utilisant un système de cryptage au niveau de l'envoi des donnés ainsi que du stockage.
Dans le cadre du projet de M2 d’application web, l’ensemble des fonctionnalités du programme ne pouvaient être complètement implémentées. Nous avons donc dû choisir de nous limiter à certaines tâches. 
Ainsi, la version application web contient :

-	La gestion des utilisateurs incluant création/modification (via une page d’administration) d’un profil ainsi que la gestion d’une liste de contact.
-	La gestion des cercles* incluant création/modification (via une page d’administration)/suppression d’un cercle. Ajout/Suppression d’utilisateurs au sein du cercle. Gestion des droits des utilisateurs dans le cercle.
-	Gestion d’un système de messagerie propre à chaque cercle et disponible uniquement pour les membres du cercle.
-	Gestion de l’hébergement de document propre à un cercle (Upload, Download)

* Un cercle est assimilable à un groupe de travail. Chaque cercle contient une liste d'utilisateurs membre et administrateur, un tchat et un système de fichier affilié ainsi que des paramètres qui lui sont propres.

### L'équipe

- Christopher Girod
- Franck Parra
- Gauthier Scampini


Installation
--------------

Le projet ayant été développé via Github, il est disponible à l’adresse suivante :
https://github.com/TeamRocketMIAGE/Projet_Cercle
La branche de travail à charger est la branche « effective ».
Voici les étapes à suivre pour son installation via Eclipse JEE :

0. File > Import…
0. Git > Projects form Git > Next
0. Clone Url > Next
0. Url: https://github.com/TeamRocketMIAGE/Projet_Cercle
0. Les champs suivants sont alors automatiquement mis à jour. Votre compte ayant été rajouté au projet github, vos identifiants permettront le chargement du projet.

La branche de travail étant la branche `effective`, toutes les autres branches sont à décocher. 

Il ne vous reste plus alors qu’à valider avec le bouton finish lors de la prochaine étape.
Le projet a maintenant été correctement importé dans le compilateur.

Pour l’exécution, la marche à suivre est la suivante.
- Dans le dossier Src/main/java => Main 
- Clique droit sur le fichier ProjetCercleApplication.java => Run as => Java Application

Le projet est maintenant disponible dans le navigateur via l’adresse :
http://localhost:8080/

### Sur le site

Vos identifiants de connexion sont les suivants:
sebastien
abc

Ce compte a déjà des cercles enregistrés ainsi que des contacts, un nouveau compte peut bien sur être créé.




