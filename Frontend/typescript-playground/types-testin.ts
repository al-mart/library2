function minimumLength<Type extends { length: number }>(
    obj: Type,
    minimum: number
): Type {
    if (obj.length >= minimum) {
        return obj;
    } else {
        return {length: minimum};
    }
}

type BooleansStringNumber = [...boolean[], string, number];


function readButtonInput( ...a: BooleansStringNumber) {

}

readButtonInput(true, true, "hello", 7);

function identity<Type>(arg: Type): Type {
    return arg;
}

let myIdentity: <Input>(arg: Input) => Input = identity;
