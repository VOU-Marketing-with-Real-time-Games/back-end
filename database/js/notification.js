db = db.getSiblingDB("notification_db");

db.createCollection("notification_user");

db.notification_user.insertMany([
    {
        content: "Welcome to our platform!",
        createdAt: new Date(),
        isRead: false,
        isDeleted: false,
        userId: 1
    },
    {
        content: "Your account settings have been updated.",
        createdAt: new Date(),
        isRead: true,
        isDeleted: false,
        userId: 2
    },
    {
        content: "Your subscription is about to expire.",
        createdAt: new Date(),
        isRead: false,
        isDeleted: false,
        userId: 3
    },
]);
