/* 
1) Ajoutez des cartes avec la commande : node card_fetcher.js -f 161503 -t 161808 -j -p http://localhost:8080/magecom-ejb/api/card
   (ne pas changer les numeros de cartes car sinon, le script ne fonctionnerait pas)

2) Executez le script suivant. Pour cela, cliquer sur l'icone en haut representant une loupe avec ecrit "sql" dedans puis
	ouvrez (ou copiez/collez) ensuite ce fichier dans la nouvelle fenetre et executez avec le bouton run. Refermez ensuite
	sans enregistrer si on vous le demande

3) Pour afficher les données d'une table dans pgSQL, il faut aller dans Magecom/Schemas/public/Tables puis faire un clic droit sur la table
	qui nous interesse et aller dans Afficher les donnees/Visualiser toutes les lignes
	
4) Si le nom des colonnes ne s'affiche pas, il faut refermer la fenetre de visualisation des donnees, faire un clic droit sur la base Magecom,
	puis selectionner Rafraichir. L'etape 3 devrait fonctionner correctement apres cela.
*/

/* 
Ajout des elements de base 
*/

INSERT INTO member(
            id, additionalinformation, address, city, country, creationdate, 
            email, firstname, lastname, password, username, zipcode)
    VALUES (nextval('member_sequence'), null, 'adresse1', 'ville1', 'pays1', '2014-11-27',
            'blabla@blabla', 'prenom1', 'PasAchat', 'pass1', 'user1', 'zip1'),
			(nextval('member_sequence'), 'aucune info', 'adresse2', 'ville2', 'pays2', '2014-11-28',
            'lilili@lilili', 'prenom2', 'PasDeckNiCollection', 'pass2', 'user2', 'zip2'),
			(nextval('member_sequence'), ' ', 'adresse3', 'ville3', 'pays3', '2014-11-29',
            'truc@truc', 'prenom3', 'PasCombo', 'pass3', 'user3', 'zip3');

INSERT INTO combo(
            id, black, blue, green, red, white, creationdate, description, 
            name, member_id)
    VALUES (nextval('combo_sequence'), true, false, false, false, false, '2014-11-27', 'Combo 1 du membre 1', 
            'Combo 1', 1),
			(nextval('combo_sequence'), false, false, false, false, false, '2014-11-27', 'Combo 2 du membre 1', 
            'Combo 2', 1),
			(nextval('combo_sequence'), false, true, true, false, false, '2014-11-28', 'Combo 1 du membre 2', 
            'Combo 3', 2),
			(nextval('combo_sequence'), true, true, true, true, true, '2014-11-28', 'Combo 2 du membre 2', 
            'Combo 4', 2);

INSERT INTO deck(
            id, black, blue, green, red, white, creationdate, description, 
            name, member_id)
    VALUES (nextval('deck_sequence'), true, false, false, false, false, '2014-11-27', 'Deck 1 du membre 1', 
            'Deck 1', 1),
			(nextval('deck_sequence'), false, false, false, false, false, '2014-11-27', 'Deck 2 du membre 1', 
            'Deck 2', 1),
			(nextval('deck_sequence'), false, true, true, false, false, '2014-11-29', 'Deck 1 du membre 3', 
            'Deck 3', 3),
			(nextval('deck_sequence'), true, true, true, true, true, '2014-11-29', 'Deck 2 du membre 3', 
            'Deck 4', 3);

INSERT INTO purchase(
            id, additionalinformation, address, city, country, creationdate, 
            firstname, lastname, total, zipcode, member_id)
    VALUES (nextval('purchase_sequence'), null, 'adresse2', 'ville2', 'pays2', '2014-11-28',
            'prenom2', 'nom2', '22.50', 'zip2', 2),
			(nextval('purchase_sequence'), null, 'adresseAutre', 'villeAutre', 'paysAutre', '2014-11-29',
            'prenomAutre', 'nomAutre', '200', 'zipAutre', 3);


/*
Ajout des Combos :

Compo des combos 1 et 3 :
161516 x2
161517 x1

Compo des combos 2 et 4 :
161517 x 3
161518 x 3
*/

INSERT INTO combotuple(
            id, quantity, card_id, combo_id)
    VALUES (nextval('tuple_sequence'), '2', 161516, 1),
			(nextval('tuple_sequence'), '1', 161517, 1),
			(nextval('tuple_sequence'), '3', 161517, 2),
			(nextval('tuple_sequence'), '3', 161518, 2),
			(nextval('tuple_sequence'), '2', 161516, 3),
			(nextval('tuple_sequence'), '1', 161517, 3),
			(nextval('tuple_sequence'), '3', 161517, 4),
			(nextval('tuple_sequence'), '3', 161518, 4);

/*
Ajout des decks :

Compo des decks 1 et 3 :
161789 x 10
161790 x 10
de 161503 a 161514 x 3
161516 x 2 fav
161517 x 1 fav
161518 x 1 fav

Compo des decks 2 et 4 :
161789 x 20
de 161516 a 161528 x 3
161530 x 1 fav
*/

INSERT INTO decktuple(
            id, favorite, quantity, card_id, deck_id)
    VALUES (nextval('tuple_sequence'), false, '10', 161789, 1),
			(nextval('tuple_sequence'), false, '10', 161790, 1),
			(nextval('tuple_sequence'), false, '3', 161503, 1),
			(nextval('tuple_sequence'), false, '3', 161504, 1),
			(nextval('tuple_sequence'), false, '3', 161505, 1),
			(nextval('tuple_sequence'), false, '3', 161506, 1),
			(nextval('tuple_sequence'), false, '3', 161507, 1),
			(nextval('tuple_sequence'), false, '3', 161508, 1),
			(nextval('tuple_sequence'), false, '3', 161509, 1),
			(nextval('tuple_sequence'), false, '3', 161510, 1),
			(nextval('tuple_sequence'), false, '3', 161511, 1),
			(nextval('tuple_sequence'), false, '3', 161512, 1),
			(nextval('tuple_sequence'), false, '3', 161513, 1),
			(nextval('tuple_sequence'), false, '3', 161514, 1),
			(nextval('tuple_sequence'), true, '2', 161516, 1),
			(nextval('tuple_sequence'), true, '1', 161517, 1),
			(nextval('tuple_sequence'), true, '1', 161518, 1),
			(nextval('tuple_sequence'), false, '20', 161789, 2),
			(nextval('tuple_sequence'), false, '3', 161516, 2),
			(nextval('tuple_sequence'), false, '3', 161517, 2),
			(nextval('tuple_sequence'), false, '3', 161518, 2),
			(nextval('tuple_sequence'), false, '3', 161519, 2),
			(nextval('tuple_sequence'), false, '3', 161520, 2),
			(nextval('tuple_sequence'), false, '3', 161521, 2),
			(nextval('tuple_sequence'), false, '3', 161522, 2),
			(nextval('tuple_sequence'), false, '3', 161523, 2),
			(nextval('tuple_sequence'), false, '3', 161524, 2),
			(nextval('tuple_sequence'), false, '3', 161525, 2),
			(nextval('tuple_sequence'), false, '3', 161526, 2),
			(nextval('tuple_sequence'), false, '3', 161527, 2),
			(nextval('tuple_sequence'), false, '3', 161528, 2),
			(nextval('tuple_sequence'), true, '1', 161530, 2),
			(nextval('tuple_sequence'), false, '10', 161789, 3),
			(nextval('tuple_sequence'), false, '10', 161790, 3),
			(nextval('tuple_sequence'), false, '3', 161503, 3),
			(nextval('tuple_sequence'), false, '3', 161504, 3),
			(nextval('tuple_sequence'), false, '3', 161505, 3),
			(nextval('tuple_sequence'), false, '3', 161506, 3),
			(nextval('tuple_sequence'), false, '3', 161507, 3),
			(nextval('tuple_sequence'), false, '3', 161508, 3),
			(nextval('tuple_sequence'), false, '3', 161509, 3),
			(nextval('tuple_sequence'), false, '3', 161510, 3),
			(nextval('tuple_sequence'), false, '3', 161511, 3),
			(nextval('tuple_sequence'), false, '3', 161512, 3),
			(nextval('tuple_sequence'), false, '3', 161513, 3),
			(nextval('tuple_sequence'), false, '3', 161514, 3),
			(nextval('tuple_sequence'), true, '2', 161516, 3),
			(nextval('tuple_sequence'), true, '1', 161517, 3),
			(nextval('tuple_sequence'), true, '1', 161518, 3),
			(nextval('tuple_sequence'), false, '20', 161789, 4),
			(nextval('tuple_sequence'), false, '3', 161516, 4),
			(nextval('tuple_sequence'), false, '3', 161517, 4),
			(nextval('tuple_sequence'), false, '3', 161518, 4),
			(nextval('tuple_sequence'), false, '3', 161519, 4),
			(nextval('tuple_sequence'), false, '3', 161520, 4),
			(nextval('tuple_sequence'), false, '3', 161521, 4),
			(nextval('tuple_sequence'), false, '3', 161522, 4),
			(nextval('tuple_sequence'), false, '3', 161523, 4),
			(nextval('tuple_sequence'), false, '3', 161524, 4),
			(nextval('tuple_sequence'), false, '3', 161525, 4),
			(nextval('tuple_sequence'), false, '3', 161526, 4),
			(nextval('tuple_sequence'), false, '3', 161527, 4),
			(nextval('tuple_sequence'), false, '3', 161528, 4),
			(nextval('tuple_sequence'), true, '1', 161530, 4);
			
/*
Ajout des purchases :

Compo du purchase 1 :
161516 x 2
161517 x 3
161518 x 3

Compo du purchase 2 :
de 161516 a 161528 x3
161530 x 1
*/

INSERT INTO purchasetuple(
            id, quantity, card_id, purchase_id)
    VALUES (nextval('tuple_sequence'), '2', 161516, 1),
			(nextval('tuple_sequence'), '3', 161517, 1),
			(nextval('tuple_sequence'), '3', 161518, 1),
			(nextval('tuple_sequence'), '3', 161516, 2),
			(nextval('tuple_sequence'), '3', 161517, 2),
			(nextval('tuple_sequence'), '3', 161518, 2),
			(nextval('tuple_sequence'), '3', 161519, 2),
			(nextval('tuple_sequence'), '3', 161520, 2),
			(nextval('tuple_sequence'), '3', 161521, 2),
			(nextval('tuple_sequence'), '3', 161522, 2),
			(nextval('tuple_sequence'), '3', 161523, 2),
			(nextval('tuple_sequence'), '3', 161524, 2),
			(nextval('tuple_sequence'), '3', 161525, 2),
			(nextval('tuple_sequence'), '3', 161526, 2),
			(nextval('tuple_sequence'), '3', 161527, 2),
			(nextval('tuple_sequence'), '3', 161528, 2),
			(nextval('tuple_sequence'), '1', 161530, 2);


/*
Ajout des collections :

Compo de la collection 1 :
de 161503 a 161514 x 3
161516 x 2
161517 x 1
161518 x 1

Compo de la collection 2 :
vide

Compo de la collection 3 :
161516 x 6
161517 x 12
161518 x 2
*/

INSERT INTO collectiontuple(
            id, quantity, card_id, member_id)
    VALUES (nextval('tuple_sequence'), '3', 161503, 1),
			(nextval('tuple_sequence'), '3', 161504, 1),
			(nextval('tuple_sequence'), '3', 161505, 1),
			(nextval('tuple_sequence'), '3', 161506, 1),
			(nextval('tuple_sequence'), '3', 161507, 1),
			(nextval('tuple_sequence'), '3', 161508, 1),
			(nextval('tuple_sequence'), '3', 161508, 1),
			(nextval('tuple_sequence'), '3', 161509, 1),
			(nextval('tuple_sequence'), '3', 161510, 1),
			(nextval('tuple_sequence'), '3', 161511, 1),
			(nextval('tuple_sequence'), '3', 161512, 1),
			(nextval('tuple_sequence'), '3', 161513, 1),
			(nextval('tuple_sequence'), '3', 161514, 1),
			(nextval('tuple_sequence'), '2', 161516, 1),
			(nextval('tuple_sequence'), '1', 161517, 1),
			(nextval('tuple_sequence'), '1', 161518, 1),
			(nextval('tuple_sequence'), '6', 161516, 3),
			(nextval('tuple_sequence'), '12', 161517, 3),
			(nextval('tuple_sequence'), '2', 161518, 3);
