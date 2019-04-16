# Comment installer le projet

Tout d'abord, on n'oublie pas de lier sa machine au compte git afin de cloner le projet.

Les étapes d'installations : 

- Installer node, npm et Vue cli
- Cloner le projet
- cd */realtime-keynote-feedback-2/projet/frontend-vuejs*

Une fois dans ce dossier, lancer les commandes suivantes : 

*npm i*

Cette commande permet de d'installer toutes les dépendances de l'application.
L'installation peut prendre un peu de temps, il faut être patient :)
Une fois terminé, exécuter ceci : 

*npm run dev*

Ceci permet de lancer le serveur. Il sera automatiquement sur le port 8080 de localhost. 
Il faudra cependant le laisser tourner en permanence !

Si on voit des erreurs dans le terminal, pas de panique ! Il suffit de lancer la commande suivante :

*npm run dev --fix*

Qui réparera tous ces problèmes. Par la suite on lancer *npm run dev* sans le fix
