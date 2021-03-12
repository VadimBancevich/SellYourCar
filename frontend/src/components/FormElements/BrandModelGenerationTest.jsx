import React, {useEffect} from "react";
import {connect} from "react-redux";
import {
    getAllBrands,
    getGenerationsByModelId,
    getModelsByBrandId,
    resetBrands,
    resetGenerations,
    resetModels,
    setGenerationId
} from "../../redux/reducers/brandModelGenerationReducer";
import {locales} from "../../locales";
import RenderAutocomplete from "./common/RenderAutocomplete";

const resetInputs = (inputs) => {
    inputs.forEach(i => i.onChange(''));
}

const BrandModelGenerationTest = props => {
    const {selectedBrandId, brands, getAllBrands, resetBrands} = props;
    const {selectedModelId, models, getModelsByBrandId, resetModels} = props;
    const {selectedGenerationId, generations, getGenerationsByModelId, setGenerationId, resetGenerations} = props;
    const {brandId, modelId, generationId} = props; //redux form props from <Fields/>
    const {isRequired = false} = props
    useEffect(() => brands.length === 0 && getAllBrands(), [brands.length])
    useEffect(() => () => resetBrands(), [])
    useEffect(() => !brandId.input.value && resetBrands(), [brandId.input])
    useEffect(() => !modelId.input.value && resetModels(), [modelId.input])
    useEffect(() => !generationId.input.value && resetGenerations(), [generationId.input])
    const handleBrandChange = (event, option) => {
        resetBrands();
        resetInputs([modelId.input, generationId.input]);
        if (option) {
            getModelsByBrandId(option.id);
        } else {
            resetInputs([brandId.input])
        }
    }
    const handleModelChange = (event, option) => {
        resetModels();
        resetInputs([generationId.input])
        option ? getGenerationsByModelId(option.id) : resetInputs([modelId.input])
    }
    const handleGenerationChange = (event, option) => {
        option ? setGenerationId(option.id) :
            (resetGenerations() && resetInputs([generationId.input]));
    }
    return (
        <>
            <RenderAutocomplete options={brands} optionLabelName={"brandName"} valueName={"id"}
                                placeholder={locales.ru.brand} isRequired={isRequired}
                                resetFormValue={!Boolean(selectedBrandId)}
                                onChanged={handleBrandChange} {...brandId}/>
            <RenderAutocomplete options={models} optionLabelName={"modelName"} valueName={"id"}
                                placeholder={locales.ru.model} isRequired={isRequired}
                                resetFormValue={!Boolean(selectedModelId)}
                                onChanged={handleModelChange} {...modelId}/>
            <RenderAutocomplete options={generations} optionLabelName={"generationName"} valueName={"id"}
                                placeholder={locales.ru.generation} isRequired={isRequired}
                                resetFormValue={!Boolean(selectedGenerationId)}
                                onChanged={handleGenerationChange} {...generationId}/>

        </>
    )
}

const mapStateToProps = state => ({
    selectedBrandId: state.bmgen.brandId,
    selectedModelId: state.bmgen.modelId,
    selectedGenerationId: state.bmgen.generationId,
    brands: state.bmgen.brands,
    models: state.bmgen.models,
    generations: state.bmgen.generations,
})

export default connect(mapStateToProps, {
    getAllBrands,
    getModelsByBrandId,
    getGenerationsByModelId,
    resetBrands,
    resetModels,
    resetGenerations,
    setGenerationId
})(BrandModelGenerationTest);