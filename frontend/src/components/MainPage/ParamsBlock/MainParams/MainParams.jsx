import React from "react";
import Years from "../../../FormElements/SearchCarForm/Years";
import BrandModelGenerationContainer from "../../../FormElements/BrandModelGeneration/BrandModelGenerationContainer";

const MainParams = (props) => {
    return (
        <div>
            <BrandModelGenerationContainer {...props}/>
            <Years {...props}/>
        </div>
    )
}

export default MainParams;