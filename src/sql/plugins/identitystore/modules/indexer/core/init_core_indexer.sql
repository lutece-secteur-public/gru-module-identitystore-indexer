--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'INDENTITY_STORE_INDEXER_INDEX_IDENTITIES';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('INDENTITY_STORE_INDEXER_INDEX_IDENTITIES','module.identitystore.indexer.adminFeature.index.identities.name',1,'jsp/admin/plugins/identitystore/modules/indexer/IndexIdentities.jsp','module.identitystore.indexer.adminFeature.index.identities.description',0,'identitystore-indexer',NULL,NULL,NULL,1);

--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'INDENTITY_STORE_INDEXER_INDEX_IDENTITIES' AND id_user = 1;
INSERT INTO core_user_right (id_right,id_user) VALUES ('INDENTITY_STORE_INDEXER_INDEX_IDENTITIES',1);