INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(0,'oauth2-id','/api/clients','GET','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8080/v1/api/clients');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(1,'oauth2-id','/api/clients/autoApprove','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''write'') and #oauth2.isUser()','http://localhost:8080/v1/api/clients/autoApprove');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(2,'oauth2-id','/api/clients','POST','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8080/v1/api/clients');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(3,'oauth2-id','/api/clients/**','PUT','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8080/v1/api/clients/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(4,'oauth2-id','/api/clients/**','DELETE','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8080/v1/api/clients/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(5,'oauth2-id','/api/resourceOwners','POST','hasRole(''ROLE_FRONTEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''write'') and #oauth2.isClient()','http://localhost:8080/v1/api/resourceOwners');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(6,'oauth2-id','/api/resourceOwners','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8080/v1/api/resourceOwners');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(7,'oauth2-id','/api/resourceOwners/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8080/v1/api/resourceOwners/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(8,'oauth2-id','/api/resourceOwners/**','DELETE','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8080/v1/api/resourceOwners/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(9,'oauth2-id','/api/resourceOwner/pwd','PATCH','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8080/v1/api/resourceOwner/pwd');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(10,'oauth2-id','/api/authorize','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8080/v1/api/authorize');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression) VALUES(11,'edge-proxy','/proxy/blacklist/client','POST','(hasRole(''ROLE_ROOT'')) and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression) VALUES(12,'edge-proxy','/proxy/blacklist/resourceOwner','POST','(hasRole(''ROLE_ADMIN'') or hasRole(''ROLE_ROOT'')) and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression) VALUES(13,'edge-proxy','/proxy/security/profile','POST','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression) VALUES(14,'edge-proxy','/proxy/security/profiles','GET','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression) VALUES(15,'edge-proxy','/proxy/security/profile/**','PUT','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression) VALUES(16,'edge-proxy','/proxy/security/profile/**','DELETE','hasRole(''ROLE_ROOT'') and #oauth2.hasScope(''trust'')');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(17,'light-store','/api/resource','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8081/v1/api/resource');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(18,'light-store','/api/resource/**','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8081/v1/api/resource/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(19,'light-store','/api/resource/**','PUT','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8081/v1/api/resource/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(20,'light-store','/api/resource/**','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8081/v1/api/resource/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(21,'light-store','/api/keyChain/**','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8081/v1/api/keyChain/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(22,'light-store','/api/keyChain/**','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8081/v1/api/keyChain/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(23,'user-profile','/api/profiles/search','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/search');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(24,'user-profile','/api/profiles','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(25,'user-profile','/api/profiles/**/addresses','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/addresses');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(26,'user-profile','/api/profiles/**/addresses/**','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/addresses/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(27,'user-profile','/api/profiles/**/addresses','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/addresses');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(28,'user-profile','/api/profiles/**/addresses/**','PUT','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/addresses/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(29,'user-profile','/api/profiles/**/addresses/**','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/addresses/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(35,'user-profile','/api/profiles/**/orders','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/orders');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(36,'user-profile','/api/profiles/**/orders/**','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/orders/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(37,'user-profile','/api/profiles/**/orders','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/orders');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(38,'user-profile','/api/profiles/**/orders/**','PUT','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/orders/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(39,'user-profile','/api/profiles/**/orders/**','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/orders/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(40,'user-profile','/api/profiles/**/cart','GET','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/cart');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(41,'user-profile','/api/profiles/**/cart','POST','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/cart');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(42,'user-profile','/api/profiles/**/cart/**','PUT','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/cart/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(43,'user-profile','/api/profiles/**/cart/**','DELETE','hasRole(''ROLE_USER'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/profiles/**/cart/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,url) VALUES(44,'product','/api/categories','GET','http://localhost:8083/v1/api/categories');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(45,'product','/api/categories','POST','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8083/v1/api/categories');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(46,'product','/api/categories/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8083/v1/api/categories/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(47,'product','/api/categories/**','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8083/v1/api/categories/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,url) VALUES(48,'product','/api/categories/**','GET','http://localhost:8083/v1/api/categories/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,url) VALUES(49,'product','/api/productDetails/**','GET','http://localhost:8083/v1/api/productDetails/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(50,'product','/api/productDetails','POST','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8083/v1/api/productDetails');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(51,'product','/api/productDetails/**','PUT','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8083/v1/api/productDetails/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(52,'product','/api/productDetails/**','DELETE','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8083/v1/api/productDetails/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(53,'product','/api/productDetails/decreaseStorageBy','PUT','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http://localhost:8083/v1/api/productDetails/decreaseStorageBy');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(54,'product','/api/productDetails/increaseStorageBy','PUT','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http://localhost:8083/v1/api/productDetails/increaseStorageBy');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(55,'product','/api/categories/all','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8083/v1/api/categories/all');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(56,'oauth2-id','/api/email/subscriber','GET','hasRole(''ROLE_BACKEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''read'') and #oauth2.isClient()','http://localhost:8080/v1/api/email/subscriber');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(57,'messenger','/api/notifyBy/email/newOrder','POST','hasRole(''ROLE_BACKEND'') and hasRole(''ROLE_FIRST_PARTY'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http://localhost:8085/v1/api/notifyBy/email/newOrder');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(58,'file-upload','/api/files','POST','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.hasScope(''write'') and #oauth2.isUser()','http://localhost:8086/v1/api/files');
INSERT INTO security_profile_list (ID,resourceID,path,method,url) VALUES(59,'file-upload','/api/files/**','GET','http://localhost:8086/v1/api/files/**');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(60,'user-profile','/api/orders','GET','hasRole(''ROLE_ADMIN'') and #oauth2.hasScope(''trust'') and #oauth2.isUser()','http://localhost:8082/v1/api/orders');
INSERT INTO security_profile_list (ID,resourceID,path,method,url) VALUES(61,'product','/api/productDetails/search','GET','http://localhost:8083/v1/api/productDetails/search');
INSERT INTO security_profile_list (ID,resourceID,path,method,expression,url) VALUES(62,'product','/api/productDetails/validate','POST','hasRole(''ROLE_BACKEND'') and #oauth2.hasScope(''trust'') and #oauth2.isClient()','http://localhost:8083/v1/api/productDetails/validate');
