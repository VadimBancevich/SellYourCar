import React from "react";
import ParamsBlockContainer from "./ParamsBlock/ParamsBlockContainer";
import Cars from "./CarsBlock/Cars";
import PaginationContainer from "./PaginationContainer";
import {reduxForm} from "redux-form";
import {findCars} from "../../redux/reducers/carsReducer";

const MainPage = (props) => {
    return (
        <div>
            <ParamsBlockContainer {...props}/>
            <Cars/>
            <PaginationContainer {...props}/>
        </div>
    )
}

export default reduxForm({
    form: 'findCars',
    onSubmit: (formData, dispatch, props) => {
        dispatch(findCars(formData));
    },
    initialValues: {page: 1}
})(MainPage);