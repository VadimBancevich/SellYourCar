import React, {useEffect, useState} from "react";
import MainParams from "./MainParams";
import {formValueSelector} from "redux-form";
import {connect} from "react-redux";
import {carApi} from "../../../../api/api";

const getBrands = (setter) => {
    carApi.getBrands().then(response => {
        setter(response.data);
    })
}
const getModels = (setter, brandId) => {
    carApi.getModelsByBrandId(brandId).then(response => {
        setter(response.data);
    })
}
const getGenerations = (setter, modelId) => {
    carApi.getGenerationsByModelId(modelId).then(response => {
        setter(response.data);
    })
}

const MainParamsContainer = (props) => {
    const {selectedBrandId, selectedModelId} = props;

    const [brands, setBrands] = useState([]);
    const [models, setModels] = useState([]);
    const [generations, setGenerations] = useState([]);

    useEffect(() => {
        getBrands(setBrands);
    }, [])
    useEffect(() => {
        if (selectedBrandId === 'all') {
            setModels([]);
            setGenerations([]);
        } else if (selectedBrandId) {
            getModels(setModels, selectedBrandId)
            props["model-id"].input.onChange('all');
            props["generation-id"].input.onChange('all');
        }
    }, [selectedBrandId])
    useEffect(() => {
        if (selectedModelId === 'all') {
            setGenerations([]);
        } else if (selectedModelId) {
            getGenerations(setGenerations,selectedModelId);
            props["generation-id"].input.onChange('all');
        }
    }, [selectedModelId])

    return <MainParams {...props} brands={brands} models={models} generations={generations}/>
}

const mapStateToProps = (state) => ({
    selectedBrandId: formValueSelector('searchCars')(state, "brand-id"),
    selectedModelId: formValueSelector('searchCars')(state, "model-id")
});

export default connect(mapStateToProps, {})(MainParamsContainer);