import {locales} from "../../locales";

const engine = [
    {title: locales.ru.gasoline, value: 'GASOLINE'},
    {title: locales.ru.diesel, value: 'DIESEL'},
    {title: locales.ru.hybrid, value: 'HYBRID'},
    {title: locales.ru.electric, value: 'ELECTRIC'}
]

const gas = [
    {value: 'METHANE', title: locales.ru.methane},
    {value: 'PROPANE', title: locales.ru.propane}
]

const transmission = [
    {value: 'AUTO', title: locales.ru.auto},
    {value: 'MANUAL', title: locales.ru.manual},
]

const drivetrain = [
    {value: 'FWD', title: locales.ru.fwd},
    {value: 'RWD', title: locales.ru.rwd},
    {value: 'AWD', title: locales.ru.awd},
]
const condition = [
    {value: 'USED', title: locales.ru.used},
    {value: 'NEW', title: locales.ru.new},
    {value: 'CRUSHED', title: locales.ru.crushed}
]

const bodyType = [
    {value: 'SEDAN', title: locales.ru.sedan},
    {value: 'WAGON', title: locales.ru.wagon},
    {value: 'HATCHBACK', title: locales.ru.hatchback},
    {value: 'ROADSTER', title: locales.ru.roadster},
    {value: 'COUPE', title: locales.ru.coupe},
    {value: 'CONVERTIBLE', title: locales.ru.convertible},
    {value: 'SUV', title: locales.ru.suv},
    {value: 'MINIVAN', title: locales.ru.minivan},
    {value: 'VAN', title: locales.ru.van},
    {value: 'PICKUP', title: locales.ru.pickup},
    {value: 'LIMOUSINE', title: locales.ru.limousine}
]

export const getLocalizedOptions = (options, locale) => {
    if (!locale || !locales[locale]) {
        locale = "ru";
    }
    return options.map(option => ({value: option.value, title: locales[locale][option.value.toLowerCase()]}))
}

export const capitalizeFirstLetter = string => {
    return string.charAt(0).toUpperCase() + string.slice(1)
}

export const formsStaticContent = {condition, engine, gas, transmission, drivetrain, bodyType};