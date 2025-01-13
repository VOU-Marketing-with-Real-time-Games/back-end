-- Wait for the primary to be ready
DO SLEEP(10);

CHANGE REPLICATION SOURCE TO
  SOURCE_HOST='mysql-primary',
  SOURCE_PORT=3306,
  SOURCE_USER='replica_user',
  SOURCE_PASSWORD='replica_pass',
  SOURCE_AUTO_POSITION=1;

START REPLICA;
SHOW REPLICA STATUS\G;

CREATE USER 'monitor_user'@'%' IDENTIFIED BY 'monitor_password';
GRANT SHOW DATABASES, SHOW STATUS, SHOW VARIABLES ON *.* TO 'monitor_user'@'%';
