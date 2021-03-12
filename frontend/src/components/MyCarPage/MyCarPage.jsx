import React, {useEffect} from "react";
import {reduxForm} from "redux-form";
import {connect} from "react-redux";
import {getMyCarById, resetUpdateCar, setUpdateCarLoading} from "../../redux/reducers/updateCarReducer";
import CarForm from "../CarForm";
import CentralCircularProgress from "../common/CentralCircularProgress";
import {resetImages} from "../../redux/reducers/imagesReducer";
import {carApi} from "../../api/api";

let MyCarPage = props => {

    const {id} = props.match.params;
    const {isLoading, setUpdateCarLoading, history} = props;

    useEffect(() => {
        props.getMyCarById(id);
        return () => {
            props.resetUpdateCar();
        }
    }, [])
    console.log(props)
    const submitHandler = formData => {
        setUpdateCarLoading(true);
        carApi.updateCar(formData)
            .then(() => history.push('/cars/' + formData.id))
            .finally(() => setUpdateCarLoading(false))
    }

    return (<>
        {isLoading ? <CentralCircularProgress/> : <CarForm {...props} submitHandler={submitHandler}/>}
    </>)
}

MyCarPage = reduxForm({form: 'updateCar'})(MyCarPage);
const mapStateToProps = state => ({initialValues: state.updateCar.car, isLoading: state.updateCar.isLoading})

MyCarPage = connect(mapStateToProps, {getMyCarById, resetUpdateCar, resetImages, setUpdateCarLoading})(MyCarPage);

export default MyCarPage;