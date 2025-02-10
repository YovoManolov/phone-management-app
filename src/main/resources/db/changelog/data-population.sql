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

INSERT INTO customers.customer (name, address, gender, age, email_contact_number, product_id)
VALUES
 ('Customer1', 'addr1', 'M', 24, 'fname_lname1@email.com', 1),
 ('Customer2', 'addr2', 'F', 21, 'fname_lname2@email.com', 2),
 ('Customer3', 'addr3', 'M', 27, 'fname_lname3@email.com', 3),
 ('Customer4', 'addr4', 'F', 34, 'fname_lname4@email.com', 4),
 ('Customer5', 'addr5', 'M', 31, 'fname_lname5@email.com', 5),
 ('Customer6', 'addr6', 'F', 36, 'fname_lname6@email.com', 6),
 ('Customer7', 'addr7', 'M', 38, 'fname_lname7@email.com', 7);

INSERT INTO subscriptions.subscription (name, price, currency, subscription_type, validity)
VALUES
 ('1GB30D', 2, 'CHF', 'prepaid', 'unlimited'),
 ('1GB30D', 3, 'CHF', 'prepaid', 'unlimited'),
 ('1GB30D', 5, 'CHF', 'prepaid', 'unlimited'),
 ('100M', 4, 'CHF', 'prepaid', 'unlimited'),
 ('ULData100M30D', 15, 'CHF', 'postpaid', '30 Days'),
 ('UL5G-ULMin30D', 30, 'CHF', 'postpaid', '30 Days');

INSERT INTO subscriptions.customer_subscription (customer_id, subscription_id)
VALUES
 (1, 1),
 (1, 2),
 (2, 5),
 (3, 6),
 (4, 1),
 (4, 3),
 (5, 6),
 (6, 6),
 (7, 2);

INSERT INTO promotions.promotion (promocode, discount, currency ,discount_type, number_of_vouchers)
VALUES
 ('PROMO23', 2, 'CHF', 'prepaid', 'unlimited'),
 ('NEWYEAR', 15, 'CHF', 'prepaid', 'unlimited'),
 ('FLAT50', 50, 'CHF', 'prepaid', 'unlimited'),
 ('FLAT100', 100, 'CHF', 'prepaid', '30 days'),
 ('EASTER', 20, 'CHF', 'prepaid', '30 days');

COMMIT;
