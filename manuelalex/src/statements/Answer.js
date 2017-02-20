/**
 * Created by Manuel on 13/02/2017.
 */

let Statement = require('./Statement.js');

module.exports = class Answer extends Statement {
    constructor(options = {}){
        super(); // todo
        this.name = options.name;
        this.propertyName = options.propertyName;
        this.type = options.type;
        this.expression = options.expression;
    }

    getGeneratedCode(type){
        return "<div>"+this.name+"<input type='"+type+"' id='"+this.propertyName+"'></div>";
    }
};