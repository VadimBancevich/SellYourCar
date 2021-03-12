import React from "react";
import {connect} from "react-redux";
import Pagination from "@material-ui/lab/Pagination";
import {Field} from "redux-form";
import {setCarPage} from "../../redux/reducers/carsReducer";

const PaginationContainer = props => {

    const {page, countPages, setCarPage} = props;


    const handleChanged = (event, selectedPage) => {
        setCarPage(selectedPage);
        props.change("page", selectedPage);
    }

    return (
        <Field name={"page"}
               component={() => <Pagination style={{width: "fit-content", margin: "0 auto"}} page={page}
                                            count={countPages} onChange={handleChanged}/>}/>
    )
}

const mapStateToProps = state => ({
    page: state.cars.page,
    countPages: state.cars.countPages,
})

export default connect(mapStateToProps, {setCarPage})(PaginationContainer);