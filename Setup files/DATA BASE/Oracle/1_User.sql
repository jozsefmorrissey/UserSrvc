
--==========================  USERS  =====================--

DROP USER AboutMe CASCADE;

CREATE USER AboutMe
IDENTIFIED BY _{AboutMe}_
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp;

GRANT connect to AboutMe;
GRANT resource to AboutMe;
GRANT CREATE SESSION TO AboutMe;
GRANT CREATE TABLE TO AboutMe;
GRANT CREATE VIEW TO AboutMe;
GRANT CREATE MATERIALIZED VIEW TO AboutMe;
GRANT DEBUG CONNECT SESSION TO AboutMe;
GRANT DEBUG ANY PROCEDURE TO AboutMe;
GRANT create session to AboutMe;
ALTER USER AboutMe QUOTA 10m ON users;

conn AboutMe/_{AboutMe}_
