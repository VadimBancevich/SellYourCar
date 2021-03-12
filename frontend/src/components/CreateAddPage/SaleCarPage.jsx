import React, {useState} from "react";
import {reduxForm} from "redux-form";
import {carApi} from "../../api/api";
import CarForm from "../CarForm";
import CentralCircularProgress from "../common/CentralCircularProgress";

const SellCarPage = (props) => {

    const [showLoader, setShowLoader] = useState(false);
    const [errorMessage, setErrorMessage] = useState(null);

    const handleSubmit = (formData) => {
        setShowLoader(true);
        carApi.createCar(formData)
            .then(res => props.history.push('/cars/my'))
            .catch(() => setErrorMessage("Failed to save car"))
            .finally(() => setShowLoader(false))
    }
    return (
        <div>
            {errorMessage && <h3>{errorMessage}</h3>}
            {showLoader && <CentralCircularProgress/>}
            <CarForm {...props} submitHandler={handleSubmit} showReset={true}/>
        </div>
    )
}

export default reduxForm({form: 'createAdForm'})(SellCarPage);

