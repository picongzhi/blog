INSERT INTO user (id, username, password, name, email) VALUES (1, 'admin', '$2a$10$wlJpitwjwGVgW5fXZ.uR.uX2qS8G7WA6WbpHH6DQ8ArxHzSvvJ8DO', 'admin', 'picongzhi@gmail.com');
INSERT INTO user (id, username, password, name, email) VALUES (2, 'pcz', '$2a$10$tZRA/vFnq68ZLDM6lXEvnurF1AQSswuAaAdIaJk62s7hv05ncJ3/i', 'picongzhi', '476371403@qq.com');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);