# realtime-keynote-feedback-2

Comment installer le projet : 

- Installer IntelliJ
- Installer Wampserver 
- Wamp --> PHPMyAdmin (on remarquera l'ironie) --> Créer une nouvelle base nommée realtime_keynote

L'architecture du projet étant entièrement refaite, il vous faudra : 
- git checkout master
- git pull
- git fetch --prune 

Pour accéder au projet : 
- localhost:8080

Pour accéder à la base de donnée : 
- localhost --> PhpMyAdmin 

Une fois ceci fait vous devriez pouvoir exécuter le projet. 

Après avoir exécuté le projet, vous devrez aller dans le fichier *application.properties* qui est dans *ressources* et :
- commenter : spring.jpa.hibernate.ddl-auto = create
- Décommenter: spring.jpa.hibernate.ddl-auto=update

Pour le moment, vous tomberez sur un hello world. La liaison entre back et front est en trian de s'effectuer ! 
