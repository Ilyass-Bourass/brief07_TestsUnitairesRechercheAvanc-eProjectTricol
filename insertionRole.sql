-- Insertion des 4 rôles avec descriptions
INSERT INTO role_app (id,name, description) VALUES (1,'ADMIN', 'Administrateur avec accès complet au système');
INSERT INTO role_app (id,name, description) VALUES (2,'MAGASINIER', 'Gestionnaire de magasin avec accès limité à son magasin');
INSERT INTO role_app (id,name, description) VALUES (3,'RESPONSABLE_ACHATS', 'Responsable avec accès étendu à plusieurs magasins');
INSERT INTO role_app (id,name, description) VALUES (4,'CHEF_ATELIER', 'Utilisateur en lecture seule');

update user_app set role_id =1 where id=1;
