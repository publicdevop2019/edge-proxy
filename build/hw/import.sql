INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(0,'oauth2-id','/auth-svc/clients','GET','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8080,'/v1/api/clients');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(1,'oauth2-id','/auth-svc/clients/autoApprove','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''write'') and #oauth2.isUser()','http','localhost',8080,'/v1/api/clients/autoApprove');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(2,'oauth2-id','/auth-svc/clients','POST','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8080,'/v1/api/clients');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(3,'oauth2-id','/auth-svc/clients/**','PUT','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8080,'/v1/api/clients/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(4,'oauth2-id','/auth-svc/clients/**','DELETE','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8080,'/v1/api/clients/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(5,'oauth2-id','/auth-svc/resourceOwners','POST','hasRole(''ROLE_FRONTEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''write'') and #oauth2.isClient()','http','localhost',8080,'/v1/api/resourceOwners');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(6,'oauth2-id','/auth-svc/resourceOwners','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8080,'/v1/api/resourceOwners');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(7,'oauth2-id','/auth-svc/resourceOwners/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8080,'/v1/api/resourceOwners/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(8,'oauth2-id','/auth-svc/resourceOwners/**','DELETE','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8080,'/v1/api/resourceOwners/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(9,'oauth2-id','/auth-svc/resourceOwner/pwd','PATCH','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8080,'/v1/api/resourceOwner/pwd');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(10,'oauth2-id','/auth-svc/authorize','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8080,'/v1/api/authorize');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(11,'oauth2-id','/auth-svc/email/subscriber','GET','hasRole(''ROLE_BACKEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''read'') and #oauth2.isClient()','http','localhost',8080,'/v1/api/email/subscriber');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(12,'oauth2-id','/auth-svc/resourceOwners/register','POST','hasRole(''ROLE_FRONTEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''write'') and #oauth2.isClient()','http','localhost',8080,'/v1/api/resourceOwners/register');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(13,'oauth2-id','/auth-svc/resourceOwners/forgetPwd','POST','hasRole(''ROLE_FRONTEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''write'') and #oauth2.isClient()','http','localhost',8080,'/v1/api/resourceOwners/forgetPwd');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(14,'oauth2-id','/auth-svc/resourceOwners/resetPwd','POST','hasRole(''ROLE_FRONTEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''write'') and #oauth2.isClient()','http','localhost',8080,'/v1/api/resourceOwners/resetPwd');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,scheme,host,port,path) VALUES(15,'oauth2-id','/auth-svc/oauth/token','POST','http','localhost',8080,'/oauth/token');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression) VALUES(16,'edge-proxy','/proxy/blacklist/client','POST','(hasRole(''ROLE_ROOT'')) and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression) VALUES(17,'edge-proxy','/proxy/blacklist/resourceOwner','POST','(hasRole(''ROLE_ADMIN'') or hasRole(''ROLE_ROOT'')) and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression) VALUES(18,'edge-proxy','/proxy/security/profile','POST','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression) VALUES(19,'edge-proxy','/proxy/security/profiles','GET','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression) VALUES(20,'edge-proxy','/proxy/security/profile/**','PUT','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression) VALUES(21,'edge-proxy','/proxy/security/profile/**','DELETE','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression) VALUES(22,'edge-proxy','/proxy/security/profile/batch/url','PATCH','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(23,'user-profile','/profile-svc/profiles/search','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/search');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(24,'user-profile','/profile-svc/profiles','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(25,'user-profile','/profile-svc/profiles/**/addresses','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/addresses');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(26,'user-profile','/profile-svc/profiles/**/addresses/**','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/addresses/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(27,'user-profile','/profile-svc/profiles/**/addresses','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/addresses');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(28,'user-profile','/profile-svc/profiles/**/addresses/**','PUT','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/addresses/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(29,'user-profile','/profile-svc/profiles/**/addresses/**','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/addresses/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(30,'user-profile','/profile-svc/profiles/**/orders/**/confirm','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/orders/**/confirm');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(31,'user-profile','/profile-svc/profiles/**/orders','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/orders');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(32,'user-profile','/profile-svc/profiles/**/orders/**','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/orders/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(33,'user-profile','/profile-svc/profiles/**/orders','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/orders');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(34,'user-profile','/profile-svc/profiles/**/orders/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/orders/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(35,'user-profile','/profile-svc/profiles/**/orders/**','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/orders/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(36,'user-profile','/profile-svc/profiles/**/cart','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/cart');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(37,'user-profile','/profile-svc/profiles/**/cart','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/cart');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(38,'user-profile','/profile-svc/profiles/**/cart/**','PUT','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/cart/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(39,'user-profile','/profile-svc/profiles/**/cart/**','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/cart/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(40,'user-profile','/profile-svc/orders','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/orders');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(41,'user-profile','/profile-svc/profiles/**/orders/**/replace','PUT','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8082,'/v1/api/profiles/**/orders/**/replace');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(42,'product','/product-svc/categories','POST','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8083,'/v1/api/categories');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(43,'product','/product-svc/categories/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8083,'/v1/api/categories/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(44,'product','/product-svc/categories/**','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8083,'/v1/api/categories/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(45,'product','/product-svc/productDetails/validate','POST','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http','localhost',8083,'/v1/api/productDetails/validate');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(46,'product','/product-svc/productDetails/sold','PUT','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http','localhost',8083,'/v1/api/productDetails/sold');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(47,'product','/product-svc/productDetails','POST','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8083,'/v1/api/productDetails');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(48,'product','/product-svc/productDetails/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8083,'/v1/api/productDetails/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(49,'product','/product-svc/productDetails/**','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8083,'/v1/api/productDetails/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(50,'product','/product-svc/productDetails/decreaseStorageBy','PUT','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http','localhost',8083,'/v1/api/productDetails/decreaseStorageBy');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(51,'product','/product-svc/productDetails/increaseStorageBy','PUT','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http','localhost',8083,'/v1/api/productDetails/increaseStorageBy');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(52,'product','/product-svc/categories/all','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http','localhost',8083,'/v1/api/categories/all');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,scheme,host,port,path) VALUES(53,'product','/product-svc/categories','GET','http','localhost',8083,'/v1/api/categories');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,scheme,host,port,path) VALUES(54,'product','/product-svc/categories/**','GET','http','localhost',8083,'/v1/api/categories/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,scheme,host,port,path) VALUES(55,'product','/product-svc/productDetails/**','GET','http','localhost',8083,'/v1/api/productDetails/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,scheme,host,port,path) VALUES(56,'product','/product-svc/productDetails/search','GET','http','localhost',8083,'/v1/api/productDetails/search');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(57,'messenger','/messenger-svc/notifyBy/email/newOrder','POST','hasRole(''ROLE_BACKEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http','localhost',8085,'/v1/api/notifyBy/email/newOrder');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(58,'messenger','/messenger-svc/notifyBy/email/activationCode','POST','hasRole(''ROLE_BACKEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http','localhost',8085,'/v1/api/notifyBy/email/activationCode');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(59,'messenger','/messenger-svc/notifyBy/email/pwdReset','POST','hasRole(''ROLE_BACKEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http','localhost',8085,'/v1/api/notifyBy/email/pwdReset');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(60,'file-upload','/file-upload-svc/files','POST','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.hasScope(''write'') and #oauth2.isUser()','http','localhost',8086,'/v1/api/files');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,scheme,host,port,path) VALUES(61,'file-upload','/file-upload-svc/files/**','GET','http','localhost',8086,'/v1/api/files/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(62,'payment','/payment-svc/paymentLink','POST','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http','localhost',8087,'/v1/api/paymentLink');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(63,'payment','/payment-svc/paymentStatus/**','GET','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http','localhost',8087,'/v1/api/paymentStatus/**');
INSERT INTO security_profile_list (ID,resource_Id,lookup_path,method,expression,scheme,host,port,path) VALUES(64,'product','/product-svc/productDetails/revoke','PUT','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http','localhost',8083,'/v1/api/productDetails/increaseStorageBy');