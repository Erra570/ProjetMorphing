
![Logo](https://i.imgur.com/MOhKs1S.png)


#

Application permettant de créer une transition entre deux images (visages ou formes)
L'image doit absolument faire 500x500 pixels !




## Auteurs

- Tom Finana
- Bastien Vigneron
- Nicolas Fontaine
- Arnaud Reignier
- Simon Charrier


## Mise en Place
Pour utiliser l'application, il faut installer l'application Java Eclipse sur Linux.

### Prérequis
- Une machine virtuelle Java fonctionnelle (17 au minimum)
### Installation
- [Téléchargez Java Eclipse](https://www.eclipse.org/downloads/download.php?file=/oomph/products/eclipse-inst-linux64.tar.gz&mirror_id=1285)
- Rendez-vous dans le dossier dans lequel à été téléchargé l'archive, faites un clique droit sur un espace vide et cliquez sur "ouvrir dans un terminal"
- Entrez la commande suivante :
```bash
tar xvfz eclipse-inst-jre-linux64.tar.gz
```
(vous pouvez fermer le terminal)
###  Demarrage
- Rendez-vous dans le dossier créé par la commande précédente et ouvre un nouveau terminal (clique droit, "ouvrir dans un terminal")
- Entrez la commande suivante :
```bash
./eclipse-inst
```
## Ouverture du projet
- Ouvrez Eclipse
- Creez un nouveau projet (file, new java project)
- Donnez-lui un nom et cliquez sur "finish"
- Copier le contenu du dossier Projet Java dans le dossier créé par Eclipse
- Sur Eclipse, faites un clique droit sur le projet, puis refresh

## JavaFx
Notre projet utilise des bibliothèques pour avoir une interface utilisateur agréable à utiliser. Il est donc nécessaire d'ajouter ces bibliothèques à Eclipse.

- [Téléchargez la bibliothèque](https://download2.gluonhq.com/openjfx/22.0.1/openjfx-22.0.1_linux-x64_bin-sdk.zip)
- Extrayez le fichier zip (clique droit, extraire)
- Ouvrez Java Eclipse
- Faites un clique droit sur le projet créé précédemment, Build Path, Configure Build Path
- Cliquez sur Modulepath
- Sur la droite, cliquez sur "Add External JARs"
- Selectionnez tous les fichiers .jar se trouvant dans javafx-sdk-22.0.1/lib
- Cliquez sur "Apply and Close"
- Sur la gauche, dans le Package Explorer, déroulez le projet et le dossier "src" (en double cliquant dessus)
- Déroulez également le package "ihm" et ouvrez "mainApp.java" (toujours en double cliquant)
- Faites un clique droit sur mainApp.java, "Run As", "Run Configurations"
- Allez dans l'onglet "Arguments"
- Dans le champ "VM arguments" copier cette commande :
```
--module-path cheminVersLeJavaFx\javafx-sdk-22.0.1\lib --add-modules javafx.controls,javafx.fxml
```
- Vous êtes fin-prêt à utiliser notre application

## Utilisation
Dans Java Eclipse, après avoir ouvert "mainApp.java", cliquez sur le bouton "Run" (rond vert avec une flèche blanche se trouvant en haut de la fenêtre)

L'application s'ouvre. Elle a deux modes d'utilisation.

### Formes Simples
Ce mode d'utilisation est utile pour des polygônes, mais aussi pour des formes relativement simples tel que des cercle, des coeur, etc

#### Placement des Points

L'interface ce présente ainsi :
![App Screenshot](https://i.imgur.com/8QwQXJy.png)

- Choix du mode permet de retourner au menu principal
- Les boutons Changer d'image vous permet de selectionner une image de début et une image d'arriver.
- Sur la gauche, pour une meilleure clarté selon les couleurs des images, vous pouvez changer la couleurs des points et de la courbe les reliants
- Vous pouvez aussi changer la vitesse du gif (par défaut il dure une seconde) en modifiant le pourcentage de vitesse (il doit être supèrieur à 5%)

Pour placer des points, il suffit de cliquer à l'endroit souhaité sur la première image. Un point miroir est créé sur la seconde image. Vous pouvez deplacer ces points en cliquant dessus et en maintenant le bouton de la souris.

- Le bouton Fermer forme permet de fermer la forme que vous êtes en train de créer, finissant automatiquement cette dernière. Vous ne pourrez plus placer de points, mais pourrez toujours les déplacer.
- Le bouton "Supprimer Dernier Point" supprime le dernier point posé des deux cotés
- Une fois la forme fermée et les points déplacés au bon endroit, cliquez sur "Commencez le morphing"

#### Chargement
Un certain temps est nécessaire pour créer le Gif. Un temps de chargement vous indiquant la progression de l'opération s'affiche :

#### Résultat
Le Gif vous es montré.

Si vous voulez récuperer ce Gif, il se trouve dans : DossierDuProjet/img/testGif.gif

### Visages
Ce mode d'utilisation est utile pour des visages. Son interface est très similaire à l'interface pour les formes. Seul les différences seront décrites.

#### Placement des Points

L'interface ce présente ainsi :
![App Screenshot](https://i.imgur.com/SIpViDM.png)

Ici, il n'est plus question de courbe, seul les points sont important. Il faut les placer comme sur l'exemple : autour du visage et sur les parties importantes du visage. Il est toujours possible de déplacer les points et on les place en cliquant sur l'image de gauche.

Il est possible de placer des points en cliquant sur l'image de droite. C'est un problème que nous n'avons pas eu le temps de résoudre. Ce sont des points corrompus. Si vous en posez un par inadvertance, le bouton "SUprimer Dernier Point" vous sortira de ce mauvais pas. (Si il est grisé, placez un point sur la partie gauche et cliquez plusieurs fois sur "Suprimer Dernier Point")

#### Chargement
Un certain temps est nécessaire pour créer le Gif. Un temps de chargement vous indiquant la progression de l'opération s'affiche :

#### Résultat
Le Gif vous es montré.

Si vous voulez récuperer ce Gif, il se trouve dans : DossierDuProjet/img/testGif.gif
