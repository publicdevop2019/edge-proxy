-- *** manually insert below record if you running it in development mode for fist time***
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(1,'838330249904134','/auth-svc/clients/user','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''write'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(0,'838330249904134','/auth-svc/clients/root','GET','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(2,'838330249904134','/auth-svc/clients/root','POST','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(125,'838330249904134','/auth-svc/clients/root/**','GET','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(126,'838330249904134','/auth-svc/clients/root/**','PATCH','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(3,'838330249904134','/auth-svc/clients/root/**','PUT','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(4,'838330249904134','/auth-svc/clients/root/**','DELETE','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(5,'838330249904134','/auth-svc/users/public','POST','hasRole(''ROLE_FRONTEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''write'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(6,'838330249904134','/auth-svc/users/admin','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(127,'838330249904134','/auth-svc/users/admin/**','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(7,'838330249904134','/auth-svc/users/admin/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(130,'838330249904134','/auth-svc/users/admin/**','PATCH','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(131,'838330249904134','/auth-svc/users/admin','PATCH','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(8,'838330249904134','/auth-svc/users/admin/**','DELETE','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(9,'838330249904134','/auth-svc/users/user/pwd','PUT','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(10,'838330249904134','/auth-svc/authorize','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(11,'838330249904134','/auth-svc/users/app','GET','hasRole(''ROLE_BACKEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''read'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(12,'838330249904134','/auth-svc/pending-users/public','POST','hasRole(''ROLE_FRONTEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''write'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(13,'838330249904134','/auth-svc/users/public/forgetPwd','POST','hasRole(''ROLE_FRONTEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''write'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(14,'838330249904134','/auth-svc/users/public/resetPwd','POST','hasRole(''ROLE_FRONTEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''write'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method) VALUES(15,'838330249904134','/auth-svc/oauth/token','POST');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(16,'838330249904141','/proxy/revoke-tokens/root','POST','(hasRole(''ROLE_ROOT'')) and #oauth2.hasScope(''trust'')');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(17,'838330249904141','/proxy/revoke-tokens/admin','POST','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'')');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(129,'838330249904141','/proxy/revoke-tokens/app','POST','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'')');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(18,'838330249904141','/proxy/endpoints/root','POST','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(19,'838330249904141','/proxy/endpoints/root','GET','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(128,'838330249904141','/proxy/endpoints/root/**','GET','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(20,'838330249904141','/proxy/endpoints/root/**','PUT','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(137,'838330249904141','/proxy/endpoints/root/**','PATCH','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(21,'838330249904141','/proxy/endpoints/root/**','DELETE','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(132,'838330249904141','/proxy/endpoints/root','DELETE','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(23,'838330249904145','/profile-svc/profiles/search','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(24,'838330249904145','/profile-svc/profiles','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(25,'838330249904145','/profile-svc/profiles/**/addresses','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(26,'838330249904145','/profile-svc/profiles/**/addresses/**','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(27,'838330249904145','/profile-svc/profiles/**/addresses','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(28,'838330249904145','/profile-svc/profiles/**/addresses/**','PUT','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(29,'838330249904145','/profile-svc/profiles/**/addresses/**','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(30,'838330249904145','/profile-svc/profiles/**/orders/**/confirm','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(31,'838330249904145','/profile-svc/profiles/**/orders','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(32,'838330249904145','/profile-svc/profiles/**/orders/**','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(33,'838330249904145','/profile-svc/profiles/**/orders/**','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(34,'838330249904145','/profile-svc/profiles/**/orders/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(35,'838330249904145','/profile-svc/profiles/**/orders/**','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(36,'838330249904145','/profile-svc/profiles/**/cart','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(37,'838330249904145','/profile-svc/profiles/**/cart','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(38,'838330249904145','/profile-svc/profiles/**/cart/**','PUT','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(39,'838330249904145','/profile-svc/profiles/**/cart/**','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(40,'838330249904145','/profile-svc/orders','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(41,'838330249904145','/profile-svc/profiles/**/orders/**/replace','PUT','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(57,'838330249904147','/messenger-svc/notifyBy/email/newOrder','POST','hasRole(''ROLE_BACKEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(58,'838330249904147','/messenger-svc/notifyBy/email/activationCode','POST','hasRole(''ROLE_BACKEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(59,'838330249904147','/messenger-svc/notifyBy/email/pwdReset','POST','hasRole(''ROLE_BACKEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(60,'838330249904148','/file-upload-svc/files','POST','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.hasScope(''write'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method) VALUES(61,'838330249904148','/file-upload-svc/files/**','GET');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(62,'838330249904149','/payment-svc/paymentLink','POST','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(63,'838330249904149','/payment-svc/paymentStatus/**','GET','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(65,'838330249904151','/bbs-svc/private/comments','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method) VALUES(66,'838330249904151','/bbs-svc/public/posts/**/comments','GET');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(67,'838330249904151','/bbs-svc/private/posts/**/comments','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(68,'838330249904151','/bbs-svc/private/comments/**','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method) VALUES(69,'838330249904151','/bbs-svc/public/posts','GET');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(70,'838330249904151','/bbs-svc/private/posts','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(71,'838330249904151','/bbs-svc/private/posts','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method) VALUES(72,'838330249904151','/bbs-svc/public/posts/**','GET');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(73,'838330249904151','/bbs-svc/private/posts/**','PUT','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(74,'838330249904151','/bbs-svc/private/posts/**','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(75,'838330249904151','/bbs-svc/private/posts/**/likes','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(76,'838330249904151','/bbs-svc/private/posts/**/likes','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(77,'838330249904151','/bbs-svc/private/comments/**/likes','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(78,'838330249904151','/bbs-svc/private/comments/**/likes','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(79,'838330249904151','/bbs-svc/private/posts/**/dislikes','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(80,'838330249904151','/bbs-svc/private/posts/**/dislikes','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(81,'838330249904151','/bbs-svc/private/comments/**/dislikes','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(82,'838330249904151','/bbs-svc/private/comments/**/dislikes','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(83,'838330249904151','/bbs-svc/private/posts/**/reports','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(84,'838330249904151','/bbs-svc/private/posts/**/reports','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(85,'838330249904151','/bbs-svc/private/comments/**/reports','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(86,'838330249904151','/bbs-svc/private/comments/**/reports','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(87,'838330249904151','/bbs-svc/private/posts/**/notInterested','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(88,'838330249904151','/bbs-svc/private/posts/**/notInterested','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(89,'838330249904151','/bbs-svc/private/comments/**/notInterested','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(90,'838330249904151','/bbs-svc/private/comments/**/notInterested','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(91,'838330249904151','/bbs-svc/admin/posts','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(92,'838330249904151','/bbs-svc/admin/posts/**','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(93,'838330249904151','/bbs-svc/admin/comments','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(94,'838330249904151','/bbs-svc/admin/comments/**','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(95,'838330249904151','/bbs-svc/admin/likes','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(96,'838330249904151','/bbs-svc/admin/dislikes','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(97,'838330249904151','/bbs-svc/admin/reports','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(98,'838330249904151','/bbs-svc/admin/notInterested','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(99,'838330249904145','/profile-svc/profiles/orders/id','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(100,'838330249904145','/profile-svc/profiles/orders/scheduler/resubmit','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(42,'838330249904146','/product-svc/catalogs/admin','POST','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(43,'838330249904146','/product-svc/catalogs/admin/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(44,'838330249904146','/product-svc/catalogs/admin/**','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(133,'838330249904146','/product-svc/catalogs/admin','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method) VALUES(114,'838330249904146','/product-svc/filters/public','GET');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(101,'838330249904146','/product-svc/catalogs/admin','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(105,'838330249904146','/product-svc/attributes/admin','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(106,'838330249904146','/product-svc/attributes/admin','POST','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(107,'838330249904146','/product-svc/attributes/admin/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(108,'838330249904146','/product-svc/attributes/admin/**','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(134,'838330249904146','/product-svc/attributes/admin','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(109,'838330249904146','/product-svc/filters/admin','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(135,'838330249904146','/product-svc/filters/admin/**','PATCH','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(110,'838330249904146','/product-svc/filters/admin/**','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(111,'838330249904146','/product-svc/filters/admin','POST','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(112,'838330249904146','/product-svc/filters/admin/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method) VALUES(53,'838330249904146','/product-svc/catalogs/public','GET');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(113,'838330249904146','/product-svc/filters/admin/**','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(136,'838330249904146','/product-svc/filters/admin','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(116,'838330249904146','/product-svc/attributes/admin/**','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(117,'838330249904146','/product-svc/catalogs/admin/**','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method) VALUES(54,'838330249904146','/product-svc/products/public','GET');
INSERT INTO biz_endpoint (ID,resource_Id,path,method) VALUES(55,'838330249904146','/product-svc/products/public/**','GET');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(103,'838330249904146','/product-svc/products/admin','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(47,'838330249904146','/product-svc/products/admin','POST','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(119,'838330249904146','/product-svc/products/admin','PATCH','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(120,'838330249904146','/product-svc/products/admin','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(52,'838330249904146','/product-svc/products/admin/**','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(48,'838330249904146','/product-svc/products/admin/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(118,'838330249904146','/product-svc/products/admin/**','PATCH','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(49,'838330249904146','/product-svc/products/admin/**','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(121,'838330249904146','/product-svc/products/app','GET','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(46,'838330249904146','/product-svc/products/app','PATCH','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(122,'838330249904146','/product-svc/products/change/app/**','DELETE','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(123,'838330249904146','/product-svc/attributes/admin/**','PATCH','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
INSERT INTO biz_endpoint (ID,resource_Id,path,method,expression) VALUES(124,'838330249904146','/product-svc/catalogs/admin/**','PATCH','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()');
