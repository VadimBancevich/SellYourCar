import React, {useEffect} from "react";
import {connect} from "react-redux";
import {initCars} from "../../../redux/reducers/carsReducer";
import CarPreview from "./CarPreview/CarPreview";
import s from "./Cars.module.css"

const Cars = (props) => {

    const {cars} = props;

    useEffect(() => props.initCars(), [])

    return (
        <div className={s.carsBlock}>
            {cars.length === 0 ?
                <h1>Ничего не найдено</h1> :
                cars.map(car => <div key={car.id}><CarPreview car={car}/></div>)}
        </div>
    )
}

const mapStateToProps = (state) => ({
    ...state.cars,
})

export default connect(mapStateToProps, {initCars})(Cars);