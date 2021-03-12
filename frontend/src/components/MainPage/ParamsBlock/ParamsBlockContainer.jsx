import React from "react";
import ParamsBlock from "./ParamsBlock";
import {findCars, setCarPage} from "../../../redux/reducers/carsReducer";
import {connect} from "react-redux";


const ParamsBlockContainer = (props) => {
    const handleFormSubmit = (formData) => {
        props.findCars(formData);
    }
    return <ParamsBlock {...props} handleFormSubmit={handleFormSubmit} page={props.page} setCarPage={props.setCarPage}/>
}


export default connect(state => ({page: state.cars.page}), {findCars, setCarPage})(ParamsBlockContainer);