var buildPath = require("path").resolve("../src/main/resources/clojure/data/xml");
module.exports = [{
    "mode": "none",
    // "devtool": "source-map",
    "entry": "./sax.js",
    "node": false,
    "output": {
        "path": buildPath,
        "filename": "sax.js",
        "library": "CLOJURE_DATA_XML_SAX",
        "libraryTarget": "var"
    }
}, {
    "mode": "production",
    // "devtool": "source-map",
    "entry": "./sax.js",
    "node": false,
    "output": {
        "path": buildPath,
        "filename": "sax.min.js",
        "library": "CLOJURE_DATA_XML_SAX",
        "libraryTarget": "var"
    }
}];
