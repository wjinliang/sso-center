INSERT INTO `d_function` VALUES ('1', '0', 'index', '1', '1', NULL, '/index', '1', NULL, NULL);
INSERT INTO `d_function` VALUES ('2', '0', 'sample', '1', '1', NULL, '/sample*', '1', NULL, NULL);

INSERT INTO `d_role` VALUES ('1', 'role-admin', '/index', '1', NULL, NULL);
INSERT INTO `d_role` VALUES ('2', 'role-user', '/sample', '1', NULL, NULL);

INSERT INTO `d_role_function` (role_id, function_id) VALUES ('1', '1');
INSERT INTO `d_role_function` (role_id, function_id) VALUES ('2', '2');

INSERT INTO `d_user` VALUES
  ('1', 'admin', '$2a$10$oWaepJdwE7OjANCEEuQCW.aSxzOCZTsJglNcDpi8cnGXRLRppNZKG', 'admin', '1', '1',
        '1', '1', NULL, NULL, '597160667@qq.com', '18600200791', '2015-10-12 00:00:00', '2015-10-12 00:00:00',
   '2015-10-12 00:00:00');
INSERT INTO `d_user` VALUES
  ('2', 'user', '$2a$10$oWaepJdwE7OjANCEEuQCW.aSxzOCZTsJglNcDpi8cnGXRLRppNZKG', 'user', '1', '1',
        '1', '1', NULL, NULL, '18600200791@163.com', '18600200791', '2015-10-12 00:00:00', '2015-10-12 00:00:00',
   '2015-10-12 00:00:00');

INSERT INTO `d_user_role` (user_id, role_id) VALUES ('1', '1');
INSERT INTO `d_user_role` (user_id, role_id) VALUES ('1', '2');
INSERT INTO `d_user_role` (user_id, role_id) VALUES ('2', '2');
