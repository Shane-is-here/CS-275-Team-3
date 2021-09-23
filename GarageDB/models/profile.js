const mongoose = require("mongoose");

const dataSchema = mongoose.Schema({

    ID: Number,
    name: String,

})

module.exports = mongoose.model("Profile", dataSchema);