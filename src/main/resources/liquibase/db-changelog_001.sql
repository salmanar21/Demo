--liquibase formatted sql
--changeset sapient:db-changelog_001 dbms:h2

CREATE TABLE PRODUCT
  (
    ID           			NUMBER (19),
    NAME				VARCHAR2(20 CHAR),
    TYPE				VARCHAR2(10 CHAR)
  ) ;
