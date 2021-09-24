const mongoose = require("mongoose");
const key = require("./keys.json");

// CONNECT TO DATABASE
mongoose.connect(key.mongoPass, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
});

// MODELS
const Data = require("./models/profile.js");

/**
 * Takes an ID and name then creates a profile to save to the database.
 * @param id The ID to find the profile for the user.
 * @param name The name of the user that will be paired with the ID.
 */
 module.exports = function saveData(id, name){
    Data.findOne({
        ID: id
    }, async (err, data) => {
        if(!data){
            const newData = new Data({
                ID: id,
                name: name,
            })
            newData.save();
            console.log("Data saved successfully.");
        } else if(err){
            console.log(err);
        }
    });
}

/**
 * Finds the data by comparing the profiles to the ID called and then
 * prints out the data within that profile.
 * @param {*} id The ID to find the profile for the user.
 */
 module.exports = function getData(id){
    Data.findOne({
        ID: id
    }, async (err, data) => {
        if(!data){
            console.log("There is no data under that ID.");
        } else if(err){
            console.log("What did you do?");
        } else {
            return `${data.ID}, ${data.name}`;
        }
    });
}