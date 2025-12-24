-- Insertion des 4 rôles avec descriptions
INSERT INTO role_app (id,name, description) VALUES (1,'ADMIN', 'Administrateur avec accès complet au système');
INSERT INTO role_app (id,name, description) VALUES (2,'MAGASINIER', 'Gestionnaire de magasin avec accès limité à son magasin');
INSERT INTO role_app (id,name, description) VALUES (3,'RESPONSABLE_ACHATS', 'Responsable avec accès étendu à plusieurs magasins');
INSERT INTO role_app (id,name, description) VALUES (4,'CHEF_ATELIER', 'Utilisateur en lecture seule');

update user_app set role_id =1 where id=1;

// Insertion des permissions pour le module PRODUIT

INSERT INTO permission (code, libelle, description, module) VALUES
            ('PRODUCT_CREATE', 'Créer un produit', 'Autorise la création d’un nouveau produit', 'PRODUIT'),

            ('PRODUCT_READ', 'Consulter les produits', 'Autorise la consultation de la liste et du détail des produits', 'PRODUIT'),

            ('PRODUCT_UPDATE', 'Modifier un produit', 'Autorise la modification des informations d’un produit', 'PRODUIT'),

            ('PRODUCT_DELETE', 'Supprimer un produit', 'Autorise la suppression d’un produit', 'PRODUIT');

// Insertion des permissions pour le module FOURNISSEUR

INSERT INTO permission (code, libelle, description, module) VALUES
             ('FOURNISSEUR_CREATE', 'Créer un fournisseur', 'Autorise la création d’un fournisseur', 'FOURNISSEUR'),
             ('FOURNISSEUR_READ', 'Consulter les fournisseurs', 'Autorise la consultation des fournisseurs', 'FOURNISSEUR'),
             ('FOURNISSEUR_UPDATE', 'Modifier un fournisseur', 'Autorise la modification d’un fournisseur', 'FOURNISSEUR'),
             ('FOURNISSEUR_DELETE', 'Supprimer un fournisseur', 'Autorise la suppression d’un fournisseur', 'FOURNISSEUR');



// Insertion des permissions pour le module BON_SORTIE

INSERT INTO permission (code, libelle, description, module) VALUES
             ('BON_SORTIE_CREATE', 'Créer un bon de sortie', 'Autorise la création d’un bon de sortie', 'BON_SORTIE'),

             ('BON_SORTIE_READ', 'Consulter les bons de sortie', 'Autorise la consultation des bons de sortie', 'BON_SORTIE'),

             ('BON_SORTIE_CANCEL', 'Annuler un bon de sortie', 'Autorise l’annulation d’un bon de sortie', 'BON_SORTIE'),

             ('BON_SORTIE_VALIDATE', 'Valider un bon de sortie', 'Autorise la validation d’un bon de sortie', 'BON_SORTIE');


// Insertion des permissions pour le module STOCK

INSERT INTO permission (code, libelle, description, module) VALUES
             ('STOCK_READ', 'Consulter le stock', 'Autorise la consultation du stock', 'STOCK'),

             ('STOCK_VALORISATION', 'Voir la valorisation du stock', 'Autorise la consultation de la valorisation du stock', 'STOCK'),

             ('STOCK_MOUVEMENT_READ', 'Consulter les mouvements de stock', 'Autorise la consultation des mouvements de stock', 'STOCK');



// Insertion des permissions pour le module COMMANDE

INSERT INTO permission (code, libelle, description, module) VALUES
             ('COMMANDE_CREATE', 'Créer une commande', 'Autorise la création d’une commande', 'COMMANDE'),

             ('COMMANDE_READ', 'Consulter les commandes', 'Autorise la consultation des commandes', 'COMMANDE'),

             ('COMMANDE_UPDATE', 'Modifier une commande', 'Autorise la modification d’une commande', 'COMMANDE'),

             ('COMMANDE_DELETE', 'Supprimer une commande', 'Autorise la suppression d’une commande', 'COMMANDE'),

             ('COMMANDE_VALIDATE', 'Valider une commande', 'Autorise la validation d’une commande', 'COMMANDE');



// Attribution de toutes les permissions au rôle ADMIN

INSERT IGNORE INTO role_permission (role_id, permission_id)
SELECT 1, p.id FROM permission p;

// Attribution des permissions spécifiques au rôle RESPONSABLE_ACHATS

INSERT IGNORE INTO role_permission (role_id, permission_id)
SELECT 3, p.id FROM permission p where p.id IN (1,2,3,4,5,6,7,8,10,13,16,17,18,19,20);


// Attribution des permissions spécifiques au rôle MAGASINIER
INSERT IGNORE INTO role_permission (role_id, permission_id)
SELECT 2, p.id FROM permission p where p.id IN (2,6,9,10,11,12,13,14,15,17,18);

// Attribution des permissions spécifiques au rôle CHEF_ATELIER
INSERT IGNORE INTO role_permission (role_id, permission_id)
SELECT 4, p.id FROM permission p where p.id IN (2,9,10,13,15,17);

// Insertion de la permission pour le module admin
INSERT INTO permission (code, libelle, description, module) VALUES
    ('ASSIGNER_ROLE', 'Assigner un rôle', 'Autorise l\'assignation de rôles aux utilisateurs', 'management_utilisateurs');

INSERT IGNORE INTO role_permission (role_id, permission_id)
SELECT 1, p.id FROM permission p WHERE p.code = 'ASSIGNER_ROLE';

INSERT IGNORE INTO user_permission (role_id, permission_id)
SELECT 1, p.id FROM permission p;





