-- Create the replication user with the correct syntax
CREATE USER 'replica_user'@'%' IDENTIFIED BY 'replica_pass';

-- Grant replication privileges
GRANT REPLICATION SLAVE ON *.* TO 'replica_user'@'%';

-- Apply the changes
FLUSH PRIVILEGES;
