Projet Cercle
=============

L'application <b>Cercle</b> est un gestionnaire de projet multi plateforme complet comprenant différents moyens de communications (vocal, écrit), un gestionnaire de fichiers sous forme d'arborescence avec différents droits au niveau des cercles, le tout utilisant un système de cryptage au niveau de l'envoi des donnés ainsi que du stockage.
Dans le cadre du projet de M2 d’application web, l’ensemble des fonctionnalités du programme ne pouvaient être complètement implémentées. Nous avons donc dû choisir de nous limiter à certaines tâches. 
Ainsi, la version application web contient :

-	La gestion des utilisateurs incluant création/modification (via une page d’administration) d’un profil ainsi que la gestion d’une liste de contacts.
-	La gestion des cercles incluant création/modification (via une page d’administration)/suppression d’un cercle. Ajout/Suppression d’utilisateurs au sein du cercle. Gestion des droits des utilisateurs dans le cercle.
-	Gestion d’un système de messagerie propre à chaque cercle et disponible uniquement pour les membres du cercle.
-	Gestion de l’hébergement de document propre à un cercle (Upload, Download)

Un cercle est assimilable à un groupe de travail. Chaque cercle contient une liste d'utilisateurs membre et administrateur, un tchat et un système de fichier affilié ainsi que des paramètres qui lui sont propres.

### L'équipe

- Christopher GIROD
- Franck PARRA
- Gauthier SCAMPINI


Installation
--------------

Le projet ayant été développé via Github, il est disponible à l’adresse suivante :
https://github.com/TeamRocketMIAGE/Projet_Cercle
La branche de travail à charger est la branche `ajoutChatFtp`.
Voici les étapes à suivre pour son installation via Eclipse JEE :

0. File > Import…
0. Git > Projects form Git > Next
0. Clone Url > Next
0. Url: https://github.com/TeamRocketMIAGE/Projet_Cercle
0. Les champs suivants sont alors automatiquement mis à jour. Votre compte ayant été rajouté au projet github, vos identifiants permettront le chargement du projet.

La branche de travail étant la branche `ajoutChatFtp`, toutes les autres branches sont à décocher. 

Il ne vous reste plus alors qu’à valider avec le bouton "finish" lors de la prochaine étape.
Le projet a maintenant été correctement importé dans Eclipse.

Pour l’exécution, la marche à suivre est la suivante.
- Dans le dossier src/main/java => Main 
- Clic droit sur le fichier ProjetCercleApplication.java => Run as => Java Application

Quelques soucis sont encore rencontrés avec le navigateur `Mozilla Firefox` au niveau de la fonctionnalité 'Chat' de l'application. Il est donc préférable de tester l'application via `Google Chrome`.

L'application Web est disponible dans le navigateur via l’adresse :
http://localhost:8080/


Utilisation de l'application Web
--------------------------------

--------------

#### Espace public

--------------

La page d'accueil permet à l'utilisateur de s'inscrire, de se connecter ou d'aller sur les pages Téléchargement, Inscription et Tarification.

- La page "Téléchargement" permettra de télécharger les différents utilitaires du programme hors application web.
- La page "Inscription" se décrit d'elle-même.
- La page "Tarification" contient les informations propres à chaque forfait qui sera proposé aux utilisateurs, allant de la gratuité à un abonnement mensuel.

--------------

#### Espace utilisateur

--------------

##### Connexion

Pour accéder à la page de login, cliquer sur le bouton "Accéder à votre espace de travail" depuis la page d'accueil.

Vos identifiants de connexion sont les suivants:
<i>Identifiant  :</i> Sebastien
<i>Mot de passe :</i> abc

Ce compte a déjà un cercle et un contact d'enregistrés. 

```
Un nouveau compte peut bien sûr être créé :
- depuis toutes les pages hors espace utilisateur du site, en cliquant sur l'onglet "Inscription" ;
- depuis la page d'accueil, en cliquant sur le bouton "Inscrivez dès vous maintenant".
```

--------------

##### Page principale

Vous êtes maintenant sur votre espace de travail. Il comprend deux zones:
- <b>Social</b> : permet l'accès direct la liste de contacts, l'ajout de contact, et éventuellement à la confirmation des demandes d'ajout (visible seulement si un ou plusieurs utilisateurs vous a envoyé une demande d'ajout).
- <b>Cercles</b> : comprend la liste des différents cercles dont l'utilisateur est membre ou administrateur, avec les informations spécifiques à chaque cercle (nom, description, administrateurs et membres).
Les paramètres des cercles dont vous êtes propriétaire (indiqués par ![alt text](http://info.catchop.fr/assets/img/glyphicons/glyphicons_051_eye_open.png "une icône représentant un oeil")) peuvent être gérés/modifiés via un clic sur le bouton ![alt text](http://shareyouride.com/assets/premium/glyphicons_pro/glyphicons_halflings/png/glyphicons_halflings_018_cog@2x.png "Roue cranté") au niveau du cercle souhaité de la liste. C'est aussi depuis cette zone que vous pouvez créer un nouveau cercle (via le bouton rouge en-dessous de la liste).


En haut à droite, un bouton avec le nom d'utilisateur permet de se déconnecter ou d'accéder aux paramètres du compte utilisateur courant. Ces dernières fonctionnalités sont accessibles au même endroit depuis toutes les pages <b>de l'espace utilisateur</b>.

--------------

##### Page d'un cercle


En cliquant sur le nom d'un cercle dans la liste de vos cercles (au niveau de la page principale de l'espace utilisateur), vous accédez à la page <b>Cercle</b>.
Cette page est décomposée en plusieurs zones.

La zone de gauche comprend:
- Cercle "nomducercle" : les différents membres du cercle sont affichés ici ainsi que leurs droits respectifs (simple membre, ou administrateur)
- Social : liste des contacts personnels de l'utilisateur (comme sur la page principale de l'espace utilisateur).

La zone centrale comprend deux onglets :
- Chat : permet de discuter avec les différents membres du cercle (note: l'historique du chat propre au cercle n'a pas encore été implémenté);
- Documents : permet l'upload/download de fichiers accessibles à tous les membres du cercle.

Pour tester ces 2 fonctionnalités (chat et partage de fichiers propres à un cercle donné), et en supposant que vous êtes sur la page du cercle <i>Google</i> (cercle dont l'utilisateur Sebastien est membre) : 

0. Gardez votre fenêtre actuelle ouverte, ouvrez en plus de cela une fenêtre de navigation privée via `Google Chrome`, et allez sur le site.
0. Toujours dans cette fenêtre de navigation privée, connectez-vous avec un compte différent du vôtre. Par exemple, avec l'identifiant Johnny (mot de passe : abc), qui est aussi membre du cercle <i>Google</i>. 
0. Toujours dans cette fenêtre de navigation privée, accédez à la page du cercle <i>Google</i>. 

Vous pouvez ainsi expérimenter le chat et le partage de fichiers, en switchant entre votre fenêtre principale du navigateur (connecté en tant que Sebastien) et votre fenêtre de navigation privée (connecté en tant que Johnny). 



