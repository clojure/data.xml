/**
 * @const
 */
var sax = function() {};

/**
 * @constructor
 * @dict
 */
sax._Node = function() {};

/**
 * @constructor
 */
sax.SAXParser = function() {};

/**
 * @constructor
 */
sax.SAXStream = function() {};

/**
 * @this {null}
 * @return {sax.SAXStream}
 */
sax.createStream = function() {};

/**
 * @this {null}
 * @return {sax.SAXParser}
 */
sax.parser = function() {};

/**
 * @this {sax.SAXParser}
 * @return {null}
 */
sax.SAXParser.prototype.write = function() {};

/**
 * @this {sax.SAXParser}
 * @return {null}
 */
sax.SAXParser.prototype.close = function() {};

/**
 * @this {sax.SAXParser}
 * @return {null}
 */
sax.SAXParser.prototype.flush = function() {};

/**
 * @this {sax.SAXParser}
 * @return {null}
 */
sax.SAXParser.prototype.resume = function() {};

/**
 * @this {sax.SAXParser}
 * @return {null}
 */
sax.SAXParser.prototype.end = function() {};

/**
 * @this {sax.SAXParser}
 */
sax.SAXParser.prototype.onopentag = function() {};

/**
 * @this {sax.SAXParser}
 * @param {sax._Node}
 * @type {function()}
 */
sax.SAXParser.prototype.onclosetag = function() {};

/**
 * @this {sax.SAXParser}
 * @param {sax._Node}
 * @type {function()}
 */
sax.SAXParser.prototype.ontext = function() {};

/**
 * @this {sax.SAXParser}
 * @param {sax._Node}
 * @type {function()}
 */
sax.SAXParser.prototype.onopencdata = function() {};

/**
 * @this {sax.SAXParser}
 * @param {sax._Node}
 * @type {function()}
 */
sax.SAXParser.prototype.oncdata = function() {};

/**
 * @this {sax.SAXParser}
 * @param {sax._Node}
 * @type {function()}
 */
sax.SAXParser.prototype.onclosecdata = function() {};

/**
 * @this {sax.SAXParser}
 * @param {sax._Node}
 * @type {function()}
 */
sax.SAXParser.prototype.onend = function() {};

/**
 * @this {sax.SAXParser}
 * @param {sax._Node}
 * @type {function()}
 */
sax.SAXParser.prototype.onerror = function() {};
