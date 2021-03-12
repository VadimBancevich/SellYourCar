import React, {useEffect} from "react";
import CarPage from "./CarPage";
import CircularProgress from "@material-ui/core/CircularProgress";
import {connect} from "react-redux";
import {getCarById, resetCarState} from "../../redux/reducers/carReducer";
import s from "../common/common.module.css"

const CarPageContainer = (props) => {
    let carId = props.match.params.id;
    const {car, getCarById} = props;
    useEffect(() => {
        props.resetCarState()
        getCarById(carId)
    }, [carId])

    return (
        car ? <CarPage car={car}/>
            : <div className={s.absCenter}><CircularProgress size={150}/></div>
    )
}

const mapStateToProps = state => ({
    car: state.car.car
})

export default connect(mapStateToProps, {getCarById, resetCarState})(CarPageContainer);
