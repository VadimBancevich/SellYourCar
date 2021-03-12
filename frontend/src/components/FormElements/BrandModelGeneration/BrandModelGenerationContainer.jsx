import React, {useEffect, useState} from "react";
import BrandModelGeneration from "./BrandModelGeneration";
import {carApi} from "../../../api/api";

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

const BrandModelGenerationContainer = (props) => {

    const [brands, setBrands] = useState([]);
    const [models, setModels] = useState([]);
    const [generations, setGenerations] = useState([]);

    const [selectedBrandId, setSelectedBrandId] = useState(null);
    const [selectedModelId, setSelectedModelId] = useState(null);
    const [selectedGenerationId, setSelectedGenerationId] = useState(null);

    const brandIdOnChange = props.brandId.input.onChange;
    const modelIdOnChange = props.modelId.input.onChange;
    const generationIdOnChange = props.generationId.input.onChange;

    const resetModels = () => {
        setModels([])
        setSelectedModelId(null)
        modelIdOnChange('')
    }

    const resetGenerations = () => {
        setGenerations([])
        setSelectedGenerationId(null)
        generationIdOnChange('')
    }

    useEffect(() => {
        getBrands(setBrands);
    }, [])

    useEffect(() => {
        resetModels()
        resetGenerations()
        if (selectedBrandId) {
            getModels(setModels, selectedBrandId)
            brandIdOnChange(selectedBrandId)
        } else {
            brandIdOnChange('')
        }
    }, [selectedBrandId])

    useEffect(() => {
        resetGenerations();
        if (selectedModelId) {
            getGenerations(setGenerations, selectedModelId);
            modelIdOnChange(selectedModelId)
        } else {
            modelIdOnChange('')
        }

    }, [selectedModelId]);

    useEffect(() => generationIdOnChange(selectedGenerationId ? selectedGenerationId : ''),
        [selectedGenerationId])
    return <BrandModelGeneration {...props} setSelectedBrandId={setSelectedBrandId}
                                 setSelectedModelId={setSelectedModelId}
                                 setSelectedGenerationId={setSelectedGenerationId}
                                 brands={brands} models={models}
                                 generations={generations}/>
}
export default BrandModelGenerationContainer;