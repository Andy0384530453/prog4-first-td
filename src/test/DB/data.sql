-- =========================================================================
-- SCRIPT D'INSERTION DES DONNÉES DE TEST (JEU DE DONNÉES COMPLET)
-- =========================================================================

-- 1. Insertion de la Catégorie de livre
INSERT INTO category (id_category, category_enum)
VALUES ('11111111-2222-3333-4444-555555555555', 'ROMAN');

-- 2. Insertion de l'Auteur
INSERT INTO author (id_author, first_name, last_name)
VALUES ('66666666-7777-8888-9999-000000000000', 'Douglas', 'Adams');

-- 3. Insertion du Livre
INSERT INTO book (id_book, title, publication_date, id_category)
VALUES (
    '9f8e7d6c-5b4a-3f2e-1d0c-9b8a7f6e5d4c',
    'Le Guide du Voyageur Galactique',
    '1979-10-12',
    '11111111-2222-3333-4444-555555555555'
);

-- 4. Table d'association entre le Livre et l'Auteur (Relation Many-To-Many)
INSERT INTO book_author (id_book, id_author)
VALUES (
    '9f8e7d6c-5b4a-3f2e-1d0c-9b8a7f6e5d4c',
    '66666666-7777-8888-9999-000000000000'
);

-- 5. Insertion de l'Exemplaire de livre (BookCopy)
INSERT INTO book_copy (id_book_copy, isbn, purchase_price, selling_price, current_stock, format_type, id_book)
VALUES (
    'a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d',
    '9782266111560',
    7.50,
    12.00,
    10,
    'POCHE',
    '9f8e7d6c-5b4a-3f2e-1d0c-9b8a7f6e5d4c'
);

-- 6. Insertion d'un Client (Customer)
INSERT INTO customer (id_customer, first_name, last_name, phone_number, email)
VALUES (
    '22222222-2222-2222-2222-222222222222',
    'Jean',
    'Jacque',
    '+2610000000',
    'jean.dupont@email.com'
);

-- 7. Insertion de la Vente (Sale) effectuée aujourd'hui (2026)
INSERT INTO sale (id_sale, sale_date, payment_status, id_customer)
VALUES (
    '33333333-3333-3333-3333-333333333333',
    '2026-06-18 14:30:00',
    'PAID',
    '22222222-2222-2222-2222-222222222222'
);

-- 8. Association de l'exemplaire acheté à la vente (SaleBook)
INSERT INTO sale_book (id_sale_book, id_sale, id_book_copy, quantity)
VALUES (
    '44444444-4444-4444-4444-444444444444',
    '33333333-3333-3333-3333-333333333333',
    'a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d',
    1
);

-- 9. Enregistrement dans l'historique des mouvements de stock (StockHistory)
INSERT INTO stock_history (id_stock_history, id_book_copy, movement_type, movement_date, quantity, sale_id, reason)
VALUES (
    '55555555-5555-5555-5555-555555555555',
    'a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d',
    'OUT',
    '2026-06-18 14:30:00',
    1,
    '33333333-3333-3333-3333-333333333333',
    'Vente client'
);