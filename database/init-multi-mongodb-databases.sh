#!/bin/bash
set -e

echo "Creating databases for NotificationService and ImageService..."

mongo <<EOF
  use notification_db;
  db.createCollection("init");
  use image_db;
  db.createCollection("init");
EOF

echo "Databases created successfully."
