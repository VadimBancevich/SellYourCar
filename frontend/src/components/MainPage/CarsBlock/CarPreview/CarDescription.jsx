import React from "react";
import {locales} from "../../../../locales";

const CarDescription = (props) => {
    let {car} = props;
    return (
        <>
            <p>{car.brand + ' ' + car.model + ' ' + car.generation}</p>
            <p>{locales.ru.year + ': ' + car.year}</p>
            <p>{locales.ru.engine + ': ' + locales.ru[car.engine.toLowerCase()]}</p>
            <p>{locales.ru.engineVolume + ': ' + car.engineVolume}</p>
            {car.gas && <p>{locales.ru.gas + ': ' + locales.ru[car.gas.toLowerCase()]}</p>}
            <p>{locales.ru.transmission + ': ' + locales.ru[car.transmission.toLowerCase()]}</p>
            <p>{locales.ru.drivetrain + ': ' + locales.ru[car.drivetrain.toLowerCase()]}</p>
            <p>{locales.ru.bodyType + ': ' + locales.ru[car.bodyType.toLowerCase()]}</p>
            <p>{locales.ru.mileage + ': ' + car.mileage}</p>
            <p>{locales.ru.condition + ': ' + locales.ru[car.condition.toLowerCase()]}</p>
            {car.vin && <p>{locales.ru.vin + ': ' + car.vin}</p>}
            {car.phone && <p>{locales.ru.phoneNumber + ': ' + car.phone}</p>}
            {car.sellerName && <p>{locales.ru.name + ': ' + car.sellerName}</p>}
            {car.description &&
            <p style={{
                whiteSpace: "break-spaces",
                maxWidth: "fit-content"
            }}>{locales.ru.description + ': ' + car.description}</p>}
        </>
    )
}

export default CarDescription;