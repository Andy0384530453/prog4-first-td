# poja-starter-template
##  Configuration et Lancement Local

Pour pouvoir lancer et tester l'application sur votre machine, vous devez configurer les variables d'environnement afin de vous connecter à notre base de données Neon.

### 1. Configuration du fichier `.env`
1. À la racine du projet, repérez le fichier `.env.example`.
2. Dupliquez-le et renommez la copie en `.env` (le fichier `.env` est automatiquement ignoré par Git pour des raisons de sécurité).
3. Remplacez la valeur `METTRE_LE_MOT_DE_PASSE_ICI` par le vrai mot de passe de notre base Neon (disponible sur message privé).

### 2. Prise en compte du `.env` dans IntelliJ IDEA
Pour que Spring Boot lise automatiquement votre fichier `.env` au démarrage :
1. Installez le plugin **EnvFile** (*Settings > Plugins > Marketplace > cherchez "EnvFile"*).
2. Ouvrez votre configuration de démarrage de l'application (en haut à droite, cliquez sur le nom de l'application principale > *Edit Configurations...*).
3. Allez sur l'onglet **EnvFile**, cochez la case **Enable EnvFile**.
4. Cliquez sur le bouton **`+`**, sélectionnez **`.env file`** et choisissez le fichier `.env` que vous venez de créer à la racine du projet.
5. Cliquez sur *Apply* puis *OK*.

Vous pouvez maintenant lancer l'application (`mvn spring-boot:run` ou via le bouton Play) !
3. Remplacez la valeur `METTRE_VOTRE_MOT_DE_PASSE_ICI` par le vrai mot de passe de notre base Neon (disponible sur notre canal privé).

### 2. Prise en compte du `.env` 


# URL de la connection
DB_URL=jdbc:postgresql://ep-dawn-cake-a2k5fgba-pooler.eu-central-1.aws.neon.tech:5432/neondb?sslmode=require&channel_binding=require

# le nom de l'user de la base de données
DB_USERNAME=neondb_owner

# MDP
DB_PASSWORD=${NOtre_mdp}  
