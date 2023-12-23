const fs = require("fs");

const jsonData = JSON.parse(fs.readFileSync('1-json.json', {encoding: "utf-8"}));
jsonData.name = "Aleksandr";
jsonData.age = 26;

fs.writeFileSync('1-json.json', JSON.stringify(jsonData));
