# Gestion Livres

## Description
Application web de gestion de bibliothèque permettant de gérer des livres et des auteurs avec authentification utilisateur.

## Technologies
- **Backend** : Java 17 + Jakarta EE 11
- **Frontend** : JSP
- **Base de données** : MySQL
- **Build** : Maven
- **Architecture** : MVC avec Servlets et Filtres

## Fonctionnalités
- ✓ Authentification utilisateur (Login/Logout)
- ✓ Gestion des livres (Ajouter, Modifier, Supprimer, Afficher)
- ✓ Gestion des auteurs (Ajouter, Modifier, Supprimer, Afficher)
- ✓ Internationalisation (FR/EN)
- ✓ Filtres de sécurité et d'authentification

## Structure du projet
src/main/java/ma/tp/gestionlivres/
├── model/ # Classes métier (Livre, Auteur, User)
├── dao/ # Accès aux données (DAO pattern)
├── servlet/ # Servlets (contrôleurs)
├── filter/ # Filtres (authentification, i18n)
└── HelloServlet.java

src/main/webapp/
├── *.jsp # Pages JSP (vue)
└── WEB-INF/ # Configuration web


## Installation
1. Configurer la base de données MySQL (db=gestion_livres)
2. Mettre à jour les paramètres de connexion dans `DBConnection.java`
3. Compiler : `mvn clean install`
4. Déployer sur un serveur d'application compatible Jakarta EE

## Auteur
Projet pédagogique - TP Gestion de Livres
