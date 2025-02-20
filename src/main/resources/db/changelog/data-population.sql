BEGIN;

-- Reset sequence for products
TRUNCATE products.product RESTART IDENTITY CASCADE;

-- Reset sequence for customers
TRUNCATE customers.customer RESTART IDENTITY CASCADE;

-- Reset sequence for subscriptions
TRUNCATE subscriptions.subscription RESTART IDENTITY CASCADE;

-- Reset sequence for customer subscriptions
TRUNCATE subscriptions.customer_subscription RESTART IDENTITY CASCADE;

-- Reset sequence for promotions
TRUNCATE promotions.promotion RESTART IDENTITY CASCADE;

-- Insert data after resetting sequences
INSERT INTO products.product (name, price, currency, discount_applied)
VALUES
  ('IPhone 14 Pro', 1349, 'CHF', false),
  ('IPhone 14', 1149, 'CHF', true),
  ('Samsung Galaxy s23', 1199, 'CHF', true),
  ('Samsung Galaxy s23 ultra', 1299, 'CHF', false),
  ('OnePlus 11', 1249, 'CHF', true),
  ('MI 13', 899, 'CHF', true),
  ('Vivo X9', 1099, 'CHF', true);

INSERT INTO customers.customer (name, address, gender, age, email_contact_number)
VALUES
 ('Customer1', 'addr1', 'M', 24, 'fname_lname1@email.com'),
 ('Customer2', 'addr2', 'F', 21, 'fname_lname2@email.com'),
 ('Customer3', 'addr3', 'M', 27, 'fname_lname3@email.com'),
 ('Customer4', 'addr4', 'F', 34, 'fname_lname4@email.com'),
 ('Customer5', 'addr5', 'M', 31, 'fname_lname5@email.com'),
 ('Customer6', 'addr6', 'F', 36, 'fname_lname6@email.com'),
 ('Customer7', 'addr7', 'M', 38, 'fname_lname7@email.com');

INSERT INTO subscriptions.subscription (name, price, currency, subscription_type, validity)
VALUES
 ('1GB30D', 2, 'CHF', 'prepaid', 'unlimited'),
 ('1GB30D', 3, 'CHF', 'prepaid', 'unlimited'),
 ('1GB30D', 5, 'CHF', 'prepaid', 'unlimited'),
 ('100M', 4, 'CHF', 'prepaid', 'unlimited'),
 ('ULData100M30D', 15, 'CHF', 'postpaid', '30 Days'),
 ('UL5G-ULMin30D', 30, 'CHF', 'postpaid', '30 Days');

INSERT INTO subscriptions.customer_subscription (customer_id, subscription_id, purchase_date, is_active)
VALUES
 (1, 1, NOW() - INTERVAL '25 days', TRUE),
 (1, 2, NOW() - INTERVAL '40 days', TRUE),
 (2, 5, NOW() - INTERVAL '10 days', TRUE),
 (3, 6, NOW() - INTERVAL '55 days', TRUE),
 (4, 1, NOW() - INTERVAL '5 days', TRUE),
 (4, 3, NOW() - INTERVAL '70 days', TRUE),
 (5, 6, NOW() - INTERVAL '20 days', TRUE),
 (6, 6, NOW() - INTERVAL '35 days', TRUE),
 (7, 2, NOW() - INTERVAL '15 days', TRUE);


INSERT INTO subscriptions.subscription (name, price, currency, subscription_type, validity)
VALUES
 ('1GB30D', 2, 'CHF', 'prepaid', 'unlimited'),
 ('1GB30D', 3, 'CHF', 'prepaid', 'unlimited'),
 ('1GB30D', 5, 'CHF', 'prepaid', 'unlimited'),
 ('100M', 4, 'CHF', 'prepaid', 'unlimited'),
 ('ULData100M30D', 15, 'CHF', 'postpaid', '30 Days'),
 ('UL5G-ULMin30D', 30, 'CHF', 'postpaid', '30 Days');


-- Insert data after resetting sequences
INSERT INTO products.product (name, price, currency, discount_applied)
VALUES
  ('IPhone 14 Pro', 1349, 'CHF', false),
  ('IPhone 14', 1149, 'CHF', true),
  ('Samsung Galaxy s23', 1199, 'CHF', true),
  ('Samsung Galaxy s23 ultra', 1299, 'CHF', false),
  ('OnePlus 11', 1249, 'CHF', true),
  ('MI 13', 899, 'CHF', true),
  ('Vivo X9', 1099, 'CHF', true);


INSERT INTO products.customer_product (customer_id, product_id, purchase_date, purchase_price, promotion_code_applied)
VALUES
  (1, 1, NOW() - INTERVAL '25 days', 1349 , FALSE),
  (2, 5, NOW() - INTERVAL '10 days', 1139 , TRUE),
  (3, 6, NOW() - INTERVAL '55 days', 899 , FALSE),
  (4, 3, NOW() - INTERVAL '70 days', 1099, TRUE),
  (5, 6, NOW() - INTERVAL '20 days', 810 , TRUE),
  (6, 6, NOW() - INTERVAL '35 days', 899, FALSE),
  (7, 2, NOW() - INTERVAL '15 days', 1149, FALSE);

INSERT INTO promotions.promotion (promocode, discount ,discount_type, number_of_vouchers)
VALUES
 ('PROMO23', 2,  '%', 10),
 ('NEWYEAR', 15, '%', 15),
 ('FLAT50', 50,  'CHF', 20),
 ('FLAT100', 100, 'CHF', 25),
 ('EASTER', 20,  'CHF', 30);

COMMIT;
