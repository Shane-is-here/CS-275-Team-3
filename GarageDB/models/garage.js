const mongoose = require("mongoose");

const dataSchema = mongoose.Schema({

    garage: Array,

})

module.exports = mongoose.model("Layout", dataSchema);