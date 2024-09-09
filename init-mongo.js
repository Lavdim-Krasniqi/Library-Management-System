db.createUser({
    user: "admin",
    pwd: "admin",
    roles: [
        {
            role: "readWrite",
            db: "library-management-system"
        }
    ]
});

// db.createCollection("myCollection");
// db.myCollection.insert({name: "Sample Document"});
