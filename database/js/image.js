db = db.getSiblingDB("image_db");

db.createCollection("image");

db.image.insertMany([
    {
        id: "1",  // Assuming MongoDB generates an ID in the model, you can insert one manually if necessary
        fileName: "image1.jpg",  // Name of the image file
        contentType: "image/jpeg",  // MIME type of the image
        content: new BinData(0, "<binary data here>")  // The image content stored as binary
    },
    {
        id: "2",
        fileName: "image2.png",
        contentType: "image/png",
        content: new BinData(0, "<binary data here>")
    },
    {
        id: "3",
        fileName: "image3.gif",
        contentType: "image/gif",
        content: new BinData(0, "<binary data here>")
    }
]);
