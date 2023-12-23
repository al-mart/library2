"use strict"
let a = Object.create({}, {
    x: {
        value: 10,
        writable: true,
        enumerable: true,
        configurable: true
    },
    y: {
        value: true
    },
    _m: {
        value: 57,
        writable: true
    },
    get m() {
        return this._m;
    },
    set m(value) {
        this._m = value;
    }
});

function hello(string, ...values) {

    return `hello from func ${values.join('-')}`;
}

let values = [1,2,3];

let x =hello`hello from me${values[0]}${values[1]}${values[2]}`;
let y =hello(`hello from me`, ...values);
console.log(x);
console.log(y);
