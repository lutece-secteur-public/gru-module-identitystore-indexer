--
-- Structure for table identitystore_identity_indexer_action
--

DROP TABLE IF EXISTS identitystore_identity_indexer_action;
CREATE TABLE identitystore_identity_indexer_action (
  id_action INT DEFAULT 0 NOT NULL,
  id_customer VARCHAR(50) DEFAULT '' NOT NULL,
  id_task INT DEFAULT 0 NOT NULL ,
  PRIMARY KEY (id_action)
  );
CREATE INDEX identitystore_identity_indexer_id_task ON identitystore_identity_indexer_action (id_task);

