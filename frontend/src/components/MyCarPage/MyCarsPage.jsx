import React, {useEffect, useState} from "react";
import {carApi} from "../../api/api";
import CarPreview from "../MainPage/CarsBlock/CarPreview/CarPreview";
import s from "../MainPage/CarsBlock/Cars.module.css"

const MyCarsPage = props => {

    const [cars, setCars] = useState(null);

    useEffect(() => {
        carApi.getMyCars().then(response => setCars(response.data))
    }, [])

    return (
        <div className={s.carsBlock}>
            {cars && cars.map(car => <CarPreview key={car.id} car={car} toUpdate={true}/>)}
        </div>
    )
}

export default MyCarsPage;