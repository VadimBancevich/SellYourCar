import React, {useEffect, useState} from "react";
import {locales} from "../../../locales";
import RenderAutocomplete from "../common/RenderAutocomplete";


const createYears = (yearFrom, yearTo) => {
    let years = [];
    while (yearFrom <= yearTo) {
        years.push(yearTo);
        yearTo--;
    }
    return years
}
const createYearsTest = (yearsFrom, yearTo) => {
    const years = [];
    for (let i = 0; yearTo >= yearsFrom; yearTo--, i++) {
        years[i] = {value: yearTo, title: yearTo.toString()}
    }
    return years;
}

const minYear = 1930;
const currentYear = new Date().getFullYear();
const initialYears = createYears(minYear, currentYear);
const initialYearsTest = createYearsTest(minYear, currentYear);

const Years = (props) => {
    const [yearsFrom, setYearsFrom] = useState(initialYearsTest);
    const [yearsTo, setYearsTo] = useState(initialYearsTest);

    const {yearFrom, yearTo} = props;

    useEffect(() => {
        if (!yearFrom.input.value && !yearTo.input.value) {
            setYearsFrom(initialYearsTest);
            setYearsTo(initialYearsTest);
        }
    }, [yearFrom.input.value, yearTo.input.value])

    const handleFrom = (event, value) => {
        debugger
        let res = createYearsTest(value ? value.value : minYear, currentYear);
        setYearsTo(res);
    }
    const handleTo = (event, value) => {
        debugger
        let res = createYearsTest(minYear, value ? value.value : currentYear);
        setYearsFrom(res)
    }


    return (
        <>
            <RenderAutocomplete input={props.yearFrom.input} options={yearsFrom} onChanged={handleFrom}
                                placeholder={locales.ru.year + ', ' + locales.ru.from.toLowerCase()}/>
            <RenderAutocomplete input={props.yearTo.input} options={yearsTo} onChanged={handleTo}
                                placeholder={locales.ru.year + ', ' + locales.ru.to.toLowerCase()}/>
        </>
    )
}

export default Years;