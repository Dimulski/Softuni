function findGTC(a, b) {
    while (a !== b) {
        if (a > b) {
            a -= b;
        } else {
            b -= a;
        }
    }

    return a;
}

console.log(findGTC(252, 105));