global.console = { 
    log: print,
    warn: print,
    error: print
};
global.xmldom = require("xmldom");
global.DOMParser = global.xmldom.DOMParser;
global.XMLSerializer = global.xmldom.XMLSerializer;
