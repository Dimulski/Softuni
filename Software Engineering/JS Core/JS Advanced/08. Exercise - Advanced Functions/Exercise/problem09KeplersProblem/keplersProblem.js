function solve(mean, ecc) {
    console.log(Number(approximate(mean, ecc, 0).toFixed(9)));

    function approximate(E0, ecc, series) {
        if (Math.abs(mean - (E0 - ecc * Math.sin(E0))) < 1e-9 || series > 200) {
            return E0;
        } else {
            return approximate(E0 - (E0 - ecc * Math.sin(E0) - mean) / (1 - ecc * Math.cos(E0)), ecc, ++series);
        }
    }
}

solve(0.25, 0.99);